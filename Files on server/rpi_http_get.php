<?php

    $file = new SplFileObject("data.txt", 'a');
    $timestamp = $_GET["data"];
    $file->fwrite($timestamp . PHP_EOL);
    $file = null;

    echo $_GET["data"];

?>