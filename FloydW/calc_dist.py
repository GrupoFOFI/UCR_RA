import math
def distancia( lat1, lat2, lon1, lon2, el1, el2):
    latDistance = math.radians(lat2 - lat1)
    lonDistance = math.radians(lon2 - lon1)
    a = math.sin(latDistance/2)*math.sin(latDistance/2) + math.cos(math.radians(lat1))*math.cos(math.radians(lat2))*math.sin(lonDistance / 2) * math.sin(lonDistance / 2)
    distance = 6371* 1000 * 2 * math.atan2(math.sqrt(a), math.sqrt(1 - a))
    height = el1-el2
    distance = distance*distance + height*height
    return  math.sqrt(distance);

p = open('puntos.txt', 'r')
Lat = []
Lng = []
i = 0;
for line in p:
    l = []
    l =line.replace('\n', '').split(',', -1)
    Lat.append(float(l[0]))
    Lng.append(float(l[1]))
    i = i +1
f = open('adyacencias.txt', 'r')
m = open('distancias.txt', 'w')
for line in f:
    l = []
    l = line.replace(')', '').replace('\n','').replace('\t','').split('(',-1)
    punto = int(l[0])
    ady = []
    ady = l[1].split(',',-1)
    for x in range(0,len(ady)):
        ady[x] = int(ady[x])
    lat1 = Lat[punto-1]
    lng1 = Lng[punto-1]
    dist = ''
    for idx,a in enumerate(ady):
        lat2 = Lat[a-1]
        lng2 = Lng[a-1]
        d = distancia(lat1,lat2,lng1,lng2,0,0)
        dist += str(d)
        dist += ','
    m.write(dist + '\n')
