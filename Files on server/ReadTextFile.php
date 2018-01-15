<?php

//nytt filobjekt med rättigheter READ
$splfileobject = new SplFileObject('./data.txt', 'r');
//specificera avskiljare i CSV-filen
$splfileobject->setCsvControl(';');

//sets flags for the SplFileObject otherwise the current() method won't work properly
$splfileobject->setFlags(SplFileObject::READ_CSV);
$arrayJSON = array();

//while tills slutet av filen (eof)
while(!$splfileobject->eof()) {
    //Returns an array
    $row = $splfileobject->current();

    $arrayJSON[] = array("name"=>$row[0], "reaction"=>$row[1]);
    $splfileobject->next(); //gå till nästa rad
}

echo json_encode($arrayJSON);

?>