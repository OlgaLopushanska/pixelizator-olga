package world.ucode;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.servlet.annotation.MultipartConfig;
import java.io.InputStream;
import java.nio.file.Paths;


@WebServlet("/upload")
@MultipartConfig
public class MainServlet extends HttpServlet {
    BufferedImage image;
    String format;
    String[] img_type = {"jpg","png","gif","bmp","tiff","jpeg"};

    //writeDB()
    BufferedImage makeGrey(BufferedImage image) {
        BufferedImage grey = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        for (int a = 0; a < image.getWidth(); a++) {
            for (int b = 0; b < image.getHeight(); b++) {
                Color c = new Color(image.getRGB(a, b));
                int Cgrey = (int) (c.getRed() * 0.299 + c.getGreen() * 0.587 + c.getBlue() * 0.114);
                Color newC = new Color(Cgrey, Cgrey, Cgrey);
                grey.setRGB(a, b, newC.getRGB());
            }
        }
        return grey;
    }

    BufferedImage makePixelized(BufferedImage image, int pix_n) {
        BufferedImage simple = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Color c = new Color(image.getRGB(0, 0));
        for (int a = 0; a < image.getWidth(); a = a + pix_n) {
            for (int b = 0; b < image.getHeight(); b = b + pix_n) {
                c = new Color(image.getRGB(a, b));
                for (int x = a; x < a + pix_n && x < image.getWidth(); x++) {
                    for (int y = b; y < b + pix_n && y < image.getHeight(); y++) {
                        simple.setRGB(x, y, c.getRGB());
                    }
                }
            }
        }
        return simple;
    }

    BufferedImage makeTriangle(BufferedImage image, int pix_n) {
        BufferedImage triangle = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Color c = new Color(image.getRGB(0, 0));
        Color e = new Color(image.getRGB(0, 0));
        int W = image.getWidth();
        int H = image.getHeight();
        int a=0,b=0;
        int x=0,y=0;
        try {
            for ( a = 0; a < W; a = a + pix_n) {
                for ( b = 0; b < H; b = b + pix_n) {
                    c = new Color(image.getRGB(a, b + pix_n - 1 < H ? b + pix_n - 1 : H - 1));
                    e = new Color(image.getRGB(a + pix_n - 1 < W ? a + pix_n - 1 : W - 1, b));
                    for ( x = a; x < a + pix_n && x < W; x++) {
                        for ( y = b; y < b + pix_n && y < H; y++) {
                            triangle.setRGB(x, y, y - b <= x - a ? c.getRGB() : e.getRGB());
                        }
                    }
                }
            }
        } catch (Exception ex){
            System.out.println(ex);
        }
        return triangle;
    }

    BufferedImage makeInversed(BufferedImage image) {
        BufferedImage inverse = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Color c = new Color(image.getRGB(0,0));
        for (int a = 0; a < image.getWidth(); a++) {
            for (int b = 0; b < image.getHeight(); b++) {
                c = new Color(image.getRGB(a, b));
                Color newC = new Color(255 - c.getRed(), 255 - c.getGreen(), 255 - c.getBlue());
                inverse.setRGB(a, b, newC.getRGB());
            }
        }
        return inverse;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            boolean correct = false;
            Part part = request.getPart("image");
            int pix_n = Integer.parseInt(request.getParameter("pix_n"));
            int pix_type = Integer.parseInt(request.getParameter("pix_type"));
            int filter_type= Integer.parseInt(request.getParameter("filter_type"));
            InputStream file = part.getInputStream();
            image = ImageIO.read(file);
            String Name = Paths.get(part.getSubmittedFileName()).getFileName().toString();
            int r = Name.lastIndexOf(".");
            format = Name.substring(r + 1);
            for(int y = 0; y < 6; y++)
                if(format.equals(img_type[y])) {
                    correct = true;
                    break;
                }
            if (correct) {
                //writeDB();
                ServletOutputStream os = response.getOutputStream();
                switch (filter_type) {
                    case 0:
                        break;
                    case 1:
                        image = makeGrey(image);
                        break;
                    case 2:
                        image = makeInversed(image);
                        break;
                }
                switch (pix_type) {
                    case 0:
                        image = makePixelized(image, pix_n);
                        break;
                    case 1:
                        image = makeTriangle(image, pix_n);
                        break;
                }
                ImageIO.write(image, format, os);
            }
        }
        catch(Exception excep){
            System.out.println(excep);
        }

    }
}
