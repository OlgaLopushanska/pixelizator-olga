let input_file = document.getElementById('inputFile');
let check = false;
let img_name = ["result.jpg","result.png","result.gif","result.bmp","result.tiff"];
let img_type2 = ["image/jpeg","image/png","image/gif","image/bmp","image/tiff"];
let n_download = 5;
let sel_file = '';

function upload(target) {
    check = false;
    if (target.files &&
        target.files.length > 0 &&
        target.files[0] &&
        target.files[0].size > 0 &&
        input_file.value !== '') {
        let image = target.files[0];
        for(let i = 0; i < n_download; i++){
            if(image.type == img_type2[i]) {
                view_image(image);
                check = true;
                document.getElementsByName('radio3')[i].checked = true;
                return;
            }
        }
        document.getElementById('file1').alt = "Упс. Что-то пошло не так. Проверь тип файла";
        document.getElementById('file1').src = "";
        check = false;
    }

    function view_image(image){
        let image2 = new Image();
        let reader = new FileReader();
        reader.onload = function () {
            document.getElementById('file1').src = reader.result;
            document.getElementById('label_size').textContent = "Размер:  " +
                (image.size/1024/1024).toFixed(2) + " МБ";
        };
        reader.readAsDataURL(image);
        check = true;
    }

}

function loaded_img(){
    document.getElementById('label_pixels').textContent = "Размер в пикселях: " +
        document.getElementById('file1').naturalWidth +
        " x " + document.getElementById('file1').naturalHeight;
    let max_p = Math.min(document.getElementById('file1').naturalWidth,
        document.getElementById('file1').naturalHeight) / 10;
    document.getElementById('range_pixel').max = max_p;
    document.getElementById('range_pixel').value = 1;
    document.getElementById('max_p').textContent = max_p.toFixed(0);
    document.getElementById('selected_p').textContent =
        document.getElementById('range_pixel').value;

}

function change_range(){
    document.getElementById('selected_p').textContent =
        document.getElementById('range_pixel').value;

}

function run_pixel() {
    sender().then(alert);
}

async function sender() {
    let forma = new FormData();
    let image = input_file.files[0];
    let pix_n = document.getElementById('range_pixel').value; //document.getElementById('pixRange').value;

    if(image != 0 && check) {
        forma.append('image', image);
        forma.set('pix_n', pix_n);
        // get pixels type
        let rad=document.getElementsByName('radio');
        for (let i=0;i<rad.length; i++) {
            if (rad[i].checked) {
                forma.set('pix_type', i);
            }
        }
        // get filter type
        let rad_f=document.getElementsByName('radio2');
        for (let i=0;i<rad_f.length; i++) {
            if (rad_f[i].checked) {
                forma.set('filter_type', i);
            }
        }
        let response = await fetch('http://localhost:8080/pixelizator_olga_war/upload',{
            method: 'POST', body: forma, enctype: 'multipart/form-data'
        });
        if (response.ok) {
            let blob = await response.blob();
            let reader = new FileReader();
            reader.onloadend = function(event){
                document.getElementById('image_res').src = event.target.result;
            };
            reader.readAsDataURL(blob);
        } else {
            alert('Can`t send message');
        }
    } else {
        alert("Check image");
    }
}

document.getElementById('file_upload3').addEventListener('click', sender);

function download() {
    if (document.getElementById('image_res').src !== "") {
        let rad=document.getElementsByName('radio3');
        let i = 0;
        for (i = 0;i<rad.length; i++) {
            if (rad[i].checked)
                break;
        }
        let OutTag = document.createElement('a');
        OutTag.setAttribute('download', img_name[i]);
        OutTag.setAttribute('href', document.getElementById('image_res').src);
        OutTag.click();
        OutTag.remove();
    } else {
        alert('Can`t find image');
    }
}
