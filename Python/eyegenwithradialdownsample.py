# -*- coding: utf-8 -*-
"""
Created on Sun Apr  2 15:05:41 2017

@author: BenJamin
"""

import random
import math
def genTesteye(n):
    test = [[0 for col in range(n)] for row in range(n)];
    for i in range(n):
        for j in range(n):
            test[i][j] = 1-2*(((i-n/2)**2+(j-n/2)**2)**0.5)/n 
            if (2*(((i-n/2)**2+(j-n/2)**2)**0.5)/n)>=1:
                test[i][j] = 0;
    return test;

def genbull(n):
    test = genTesteye(n)
    for i in range(n):
        for j in range(n):
            if (((i-n/2)**2+(j-n/2)**2)**0.5)/n<0.3:
               test[i][j] = test[int(n/4)][int(n/2)] + random.uniform(-.025, .05);
    return test

def gensmilel(n):
    test = genTesteye(n)
    for i in range(n):
        for j in range(n):
            if (((i-n/2)**2+(j-n/2)**2)**0.5)/n<0.5 and (((i-n/2)**2+(j-n/2)**2)**0.5)/n>0.4 and i<n/2:
               test[i][j] = test[i][j] + random.uniform(.1, .2);
    return test
    
def gensmiler(n):
    test = genTesteye(n)
    for i in range(n):
        for j in range(n):
            if (((i-n/2)**2+(j-n/2)**2)**0.5)/n<0.5 and (((i-n/2)**2+(j-n/2)**2)**0.5)/n>0.4 and i>n/2:
               test[i][j] = test[i][j] + random.uniform(.1, .2);
    return test

def gensmiled(n):
    test = genTesteye(n)
    for i in range(n):
        for j in range(n):
            if (((i-n/2)**2+(j-n/2)**2)**0.5)/n<0.5 and (((i-n/2)**2+(j-n/2)**2)**0.5)/n>0.4 and j<n/2:
               test[i][j] = test[i][j] + random.uniform(.1, .2);
    return test

def gensmileu(n):
    test = genTesteye(n)
    for i in range(n):
        for j in range(n):
            if (((i-n/2)**2+(j-n/2)**2)**0.5)/n<0.5 and (((i-n/2)**2+(j-n/2)**2)**0.5)/n>0.4 and j>n/2:
               test[i][j] = test[i][j] + random.uniform(.1, .2);
    return test
    

def raddown1(f1,k):
#    k = 0;
#    k = genbull(50);

    r=0;
    l=[];
    q=[];
    while r<24:
        l.append([]);
        l[r].append([]);
        l[r].append([]);
        l[r].append([]);
        l[r].append([]);
        for i in range(50):
             for j in range(50):
                 if (((24.5-i)**2+(24.5-j)**2)**0.5)>=r and(((24.5-i)**2+(24.5-j)**2)**0.5)<r+1 :
                     if i< 24.5 and j< 24.5:
                         l[r][0].append(k[i][j])
                     if i>= 24.5 and j< 24.5:
                         l[r][1].append(k[i][j])
                     if i>= 24.5 and j>= 24.5:
                         l[r][2].append(k[i][j])
                     if i< 24.5 and j>= 24.5:
                         l[r][3].append(k[i][j])
        r+=1;
    for i in l:
        for j in i:
            q.append(sum(j)/len(j))
    if q != 0:
      for r in range(96):
              f1.write(str(q[r]))
              f1.write(',')
              
              
def raddown2(f1,k):
#    k = 0;
#    k = genbull(50);
    
    r=0;
    l=[];
    q=[];
    while r<24:
        l.append([]);
        l[r].append([]);
        l[r].append([]);
        l[r].append([]);
        l[r].append([]);
        for i in range(50):
             for j in range(50):
                 if (((24.5-i)**2+(24.5-j)**2)**0.5)>=r and(((24.5-i)**2+(24.5-j)**2)**0.5)<r+1 :
                     if math.tan((i-24.5)/(j-24.5))>=0 and math.tan((i-24.5)/(j-24.5))<2*math.pi/4 :
                         if i-24.5<0:
                             l[r][0].append(k[i][j])
                         else:
                             l[r][2].append(k[i][j])
                
                     if math.tan((i-24.5)/(j-24.5))<=0 and math.tan((i-24.5)/(j-24.5))>-2*math.pi/4 :
                         if i-24.5<0:
                             l[r][1].append(k[i][j])
                         else:
                             l[r][3].append(k[i][j])
        r+=1;
    for i in l:
            for j in i:
                print(len(j))
                q.append(sum(j)/len(j))
    if q != 0:
            for r in range(96):
              f1.write(str(q[r]))
    
def raddown3(f1,k):
#    k = 0;
#    k = genbull(50);

    r=0;
    l=[];
    q=[];
    while r<24:
        l.append([]);
        l[r].append([]);
        l[r].append([]);
        l[r].append([]);
        l[r].append([]);
        l[r].append([]);
        l[r].append([]);
        l[r].append([]);
        l[r].append([]);
        for i in range(50):
             for j in range(50):
                 if (((24.5-i)**2+(24.5-j)**2)**0.5)>=r and(((24.5-i)**2+(24.5-j)**2)**0.5)<r+1 :
                     if i< 24.5 and j< 24.5:
                         if (i-24.5)**2<(j-24.5)**2:
                             l[r][0].append(k[i][j])
                         if (i-24.5)**2>(j-24.5)**2:
                             l[r][1].append(k[i][j])
                         else:
                             l[r][0].append(k[i][j])
                             l[r][1].append(k[i][j])
                     if i>= 24.5 and j< 24.5:
                         if (i-24.5)**2<(j-24.5)**2:
                             l[r][2].append(k[i][j])
                         if (i-24.5)**2>(j-24.5)**2:
                             l[r][3].append(k[i][j])
                         else:
                             l[r][2].append(k[i][j])
                             l[r][3].append(k[i][j])
                     if i>= 24.5 and j>= 24.5:
                         if (i-24.5)**2<(j-24.5)**2:
                             l[r][4].append(k[i][j])
                         if (i-24.5)**2>(j-24.5)**2:
                             l[r][5].append(k[i][j])
                         else:
                             l[r][4].append(k[i][j])
                             l[r][5].append(k[i][j])
                     if i< 24.5 and j>= 24.5:
                         if (i-24.5)**2<(j-24.5)**2:
                             l[r][6].append(k[i][j])
                         if (i-24.5)**2>(j-24.5)**2:
                             l[r][7].append(k[i][j])
                         else:
                             l[r][6].append(k[i][j])
                             l[r][7].append(k[i][j])
        r+=1;
    for i in l:
            for j in i:
                print(len(j))
                q.append(sum(j)/len(j))
    if q != 0:
            for r in range(192):
              f1.write(str(q[r]))
              f1.write(',')

def manyraddown(file,n):
    f1 = open(file,"a+")
    for i in range(n):
        k = 0;
        k = genbull(50);
        raddown1(f1,k)
        f1.write('1,0,0,0,0\n')
               
        k = 0
        k = gensmiled(50)
        raddown1(f1,k)
        f1.write('0,0,0,1,0\n')
        k = 0
        k = gensmiler(50)
        raddown1(f1,k)
        f1.write('0,0,1,0,0\n')
        k = 0
        k = gensmilel(50)
        raddown1(f1,k)
        f1.write('0,1,0,0,0\n')
        k = 0
        k = gensmileu(50)
        raddown1(f1,k)
        f1.write('0,0,0,0,1\n')
    f1.close()
def manyr2(file,n):
    f1 = open(file,"a+")
    for i in range(n):
        k = 0;
        k = genbull(50);
        raddown3(f1,k)
        f1.write('1,0,0,0,0\n')
    f1.close()