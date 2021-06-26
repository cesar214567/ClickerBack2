import pandas as pd
import csv
import numpy as np


from pandas.io.parsers import read_csv
df = read_csv('autoseminuevo.csv',delimiter=';',header=None)
headers = df.columns 
df[27] = df[31]
df[3] = df[3]*1000
df.pop(31)
df.to_csv('autoseminuevo2.csv',sep=';',float_format='%.0f',header = False,index=False)