import random
import time

def isSorted(result):
    prev = result[0]
    for i in result:
        if prev>i:
            return False
    return True

def selectionSort(array):
    tik = time.time()
    for i in range(0, len(array)):
        m = i
        for j in range(i+1, len(array)):
            if array[j] < array[m]:
                m = j
            array[i], array[m] = array[m], array[i]
    tok = time.time()
    print(tok-tik)


def insertionSort(array):
    tik = time.time()
    for i in range(1,len(array),1):
        for j in range(i, 0, -1):
            if array[j-1] < array[j]:
                break
            array[j], array[j-1] = array[j-1], array[j]
    tok = time.time()
    print("Insertion: ", tok-tik)
    assert isSorted(array)
    # print(array)


def shellSort(array):
    tik = time.time()
    h = 1
    while h < int(len(array)/3): 
        h = 3*h + 1
    while h>=1:
        for i in range(h, len(array)):
            j=i
            while j>=h and array[j] < array[j-h]:
                array[j], array[j-h] = array[j-h], array[j]
                j-=h
        h = int(h/3) 
    tok = time.time()
    print("Shell: ", tok-tik)
    assert isSorted(array)
    


def mergeSort(array):
    tik = time.time()
    global aux
    aux = array.copy()

    def merge(a, lo, mid, hi):
        if isSorted(a[lo:hi]):
            return
        i, j = lo, mid+1
        aux[lo:hi] = a[lo:hi]
        for k in range(lo, hi+1):
            if i>mid:
                a[k] = aux[j]
                j+=1
            elif j > hi:
                a[k] = aux[i]
                i+=1
            elif aux[j] < aux[i]:
                a[k] = aux[j]
                j+=1
            else:
                a[k] = aux[i]
                i+=1

    def sort(a, lo, hi):
        if hi<=lo:
            return
        mid = lo + int((hi-lo)/2)
        sort(a, lo, mid, )
        sort(a, mid + 1, hi, )
        merge(a, lo, mid, hi, )
        return a

    array = sort(array, 0, len(array)-1, )
    tok = time.time()
    print("Merge: ", tok-tik)
    assert isSorted(array)



def mergeSortBU(array):
    tik = time.time()
    global aux
    global N
    aux = array.copy()
    N = len(array)


    def merge(a, lo, mid, hi):
        if isSorted(a[lo:hi]):
            return
        i, j = lo, mid+1
        aux[lo:hi] = a[lo:hi]
        for k in range(lo, hi+1):
            if i>mid:
                a[k] = aux[j]
                j+=1
            elif j > hi:
                a[k] = aux[i]
                i+=1
            elif aux[j] < aux[i]:
                a[k] = aux[j]
                j+=1
            else:
                a[k] = aux[i]
                i+=1

    def sort(a):
        sz = 1
        for _ in range(N):
            lo = 0
            if sz>= N:
                break
            for _ in range(N-sz):
                if lo >= N-sz:
                    break
                merge(a, lo, lo+sz-1, min(lo+sz+sz-1, N-1))
                lo = lo+sz+sz
            sz = sz+sz
        return(a)
            

    array = sort(array)
    tok = time.time()
    print("MergeBU: ", tok-tik)
    assert isSorted(array)



if __name__=='__main__':
    array = [int(random.uniform(0,100)) for i in range(35168432)]
    shellSort(array)
    mergeSort(array)
    mergeSortBU(array)