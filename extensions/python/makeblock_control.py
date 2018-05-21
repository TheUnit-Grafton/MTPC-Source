#import sys.path
#sys.path.append(EXTENSION_DIR);
import xy.xy.turtles

#import random
#import math

class VirtualRobot:
    def __init__(self):
        self.x=0;
        self.y=0;
        self.onpaper=False
        print("START")
    def goto(x, y):
        print("GOTO X:"+x+" Y:"+y)
        self.x=x
        self.y=y
    def press():
        self.onpaper=not self.onpaper;
        print("ONPAPER:"+self.onpaper)
    def setOnpaper(paper):
        self.onpaper=paper;
        print("ONPAPER:"+self.onpaper)

"""class DrawImage:
    def useRobot(x, y, direction, image):
        neighbors=[[x+1,y],[x-1,y],[x,y-1],[x,y+1],[x-1,y+1],[x+1,y-1],[x-1,y-1],[x+1,y+1]]
        index=0;
        for xw,yw in neigbors:
            index+=1;
            if (xw != -1 and xw != image.w and yw != -1 and yw != image.h):
                if (index==direction):
                    print "o"""

class Punkt:
    def __init__(self, x, y):
        self.x=x
        self.y=y

def pythagoras(xd, yd):
    return math.sqrt(xd*xd+yd*yd)

class Strecke:
    def __init__(self, p, q):
        self.p=p
        self.q=q
        self.distance=pythagoras(q.x-p.x, q.y-p.y)

rekord=100000000;
bester_pfad=[]
def kuerzesterWeg(zurueckgelegt, pfad, p, strecken):
    global rekord;
    global bester_pfad;
    if (len(strecken) == 0):
        if (zurueckgelegt < rekord):
            bester_pfad=[]
            for strecke in pfad:
                bester_pfad.append(strecke)
            rekord=zurueckgelegt
        return
    else:
       index=0
       for s in strecken:
           strecken_copy=strecken[:]
           del strecken_copy[index]
           pw=s.p
           qw=s.q
           weg=pythagoras(pw.x-p.x, pw.y-p.y)+pythagoras(pw.x-qw.x, pw.y-qw.y)
           pfad_copy=pfad[:]
           pfad_copy.append(Strecke(pw,qw))
           kuerzesterWeg(zurueckgelegt+weg, pfad_copy, qw, strecken_copy)
           weg=pythagoras(qw.x-p.x, qw.y-p.y)+pythagoras(pw.x-qw.x, pw.y-qw.y)
           pfad_copy=pfad[:]
           pfad_copy.append(Strecke(qw,pw))
           kuerzesterWeg(zurueckgelegt+weg, pfad_copy, pw, strecken_copy)
           index=index+1

kuerzesterWeg(0, [], Punkt(0,0), inputl)
print(bester_pfad)
print(rekord)

def main():
    t = xy.Turtle()
    inputl=[]
    for i in range(0,4):
        inputl.append(Strecke(Punkt(random.random()*200, random.random()*200),Punkt(random.random()*200, random.random()*200)))
    kuerzesterWeg(0, [], Punkt(0,0), inputl)
    for s in bester_pfad:
        t.pu()
        t.goto(s.p.x, s.p.y)
        t.pd()
        t.goto(s.q.x, s.q.y)

    drawing = t.drawing.rotate_and_scale_to_fit(315, 380, step=90).scale(1, -1).origin()
    drawing.render().write_to_png('dragon.png')
    xy.draw(drawing)



if __name__ == '__main__':
    main()
STATUS="RUN COMPLETED"
