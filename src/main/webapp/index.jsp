<%--
  Created by IntelliJ IDEA.
  User: Olga
  Date: 18.11.2020
  Time: 22:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>PIXELIZATOR</title>
    <link rel="stylesheet" href="style.css">
    <link rel="javascript" href="myjs.js">
</head>
<body>
<header>
    <h1><strong>ПИКСЕЛИЗАТОР</strong></h1>
    <p><strong>Загрузите картинку</strong></p>
    <input id="inputFile" class="buttons" type="file" accept="image/*" onchange="upload(this)">
</header>
<section>
    <p class="pictures1">
        <img id="file1" src=""width="400" onload="loaded_img()">
        <img  id="image_res" src="" width="400">
    </p>
</section>
<aside>
    <p> <label id="label_size">Размер в байтах MB</label></p>
    <p><label id="label_pixels">Размер в пикселях</label></p>
    <p>Выбрать размер пикселя</p>
    <label>1</label>
    <input type="range"
           id = "range_pixel"
           min="1"
           max="20"
           step="1"
           value="1" name="pixel" onchange="change_range()">
    <label id="max_p">20</label>
    <p><label >Коэффициент = </label>
        <label id="selected_p">1</label>
    </p>
    <p><label id="label-shape">Выберите форму элемента</label></p>
    <div class="element_radio">
        <input id="radio-1" type="radio" name="radio" value="1" checked>
        <label for="radio-1">квадрат</label>
        <input id="radio-2" type="radio" name="radio" value="2">
        <label for="radio-2">треугольник</label>
    </div>
    </p>
    <p><label id="label-filter">Выберите фильтр</label></p>
    <div class="filter_radio">
        <input id="radio-fil0" type="radio" name="radio2" value="0" checked>
        <label for="radio-fil0">откл.</label>
        <input id="radio-fil1" type="radio" name="radio2" value="1">
        <label for="radio-fil1">черно-белый</label>
        <input id="radio-fil2" type="radio" name="radio2" value="2">
        <label for="radio-fil2">негатив</label>
    </div>
    <p><input id="file_upload3" class="buttons_load" type="button" value="Пикселизировать"></p>
    <p><label id="label_img_type">Сохранить результат как</label></p>
    <div class="img_type">
        <input id="img_type0" type="radio" name="radio3" value="0" checked>
        <label for="img_type0">jpg</label>
        <input id="img_type1" type="radio" name="radio3" value="1" >
        <label for="img_type1">png</label>
        <input id="img_type2" type="radio" name="radio3" value="2" >
        <label for="img_type2">gif</label>
        <input id="img_type3" type="radio" name="radio3" value="3" >
        <label for="img_type3">bmp</label>
        <input id="img_type4" type="radio" name="radio3" value="4" >
        <label for="img_type4">tiff</label>
    </div>
    <p><input id="file_download" class="buttons_load" type="button" value="Сохранить" onclick="download()"></p>
</aside>
<script src="myjs.js"></script>
</body>
</html>
