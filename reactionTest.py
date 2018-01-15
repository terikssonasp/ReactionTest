# -*- coding: utf-8 -*-
from gpiozero import LED, Button
from random import uniform
import datetime
import time
import urllib.request, urllib

led = LED(4)
button = Button(15)




while True:
    newgame=(input('New test?? [y/n] '))
    if(newgame == 'y'):
        name=(input('Enter your name: '))
        led.on()
        time.sleep(uniform(5,10))
        led.off()
        start = datetime.datetime.now()

        def pressed(btn):
            print(str(start))
            stop = datetime.datetime.now()
            print(str(stop))
            elapsed = (start - stop)
            output = abs(elapsed.total_seconds())
            #print(name + ' reacted in seconds: ' + str(output))

            #path = 'http://localhost/post_data.php?data=' + name + ";" + str(output)
            path = 'http://users.du.se/~h15aspto/IK2018Labb4/rpi_http_get.php?data=' + name + ";" + str(output) +"; "
            test = urllib.request.urlopen(path)
            print(test.read())
            

        button.when_pressed = pressed

        time.sleep(5)
    else:
        exit()


