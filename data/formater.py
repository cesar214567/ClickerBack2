import pandas as pd
import csv
f = open('autoseminuevo.csv', 'w')
f2 = open('relaciones.csv','w')
df = pd.read_csv("data.csv")
for index,row in df.iterrows():
    data= []
    data.append(index)
    data.append(str(row['Año fabricación/modelo']).split("/")[0])
    data.append('0')
    data.append(row['Motor'])
    data.append(row['Color'])
    data.append('false')
    data.append('')
    data.append(str(row['Descripción']).replace('\n','').replace(';',''))
    data.append('true')
    data.append('')
    data.append('2021-06-21 15:33:43.444600')
    data.append('')
    data.append('false')
    data.append(int(str(row['Kilometraje']).replace(',','').replace(' ','')))
    data.append('1')
    data.append(row['Disponible en'])
    data.append('')
    data.append(row['Marca'])
    data.append(row['Modelo'])
    data.append(row['Datos Propietario'])
    data.append(row['# Cilindros'])
    data.append('4')
    data.append(row['Placa'])
    data.append(int (str(row['Precio Final']).replace('$','').replace(',','')))
    data.append('true')
    data.append('')
    data.append(row['Celular Propietario'])
    data.append('')
    data.append(row['Tipo de Carrocería'])
    data.append(row['Combustible'])
    data.append(row['Tracción'])
    data.append(row['Transmisión'])
    data.append('true')
    data.append('true')
    data.append(str(row['Versión']).replace('\n',''))
    data.append('')
    data.append('')
    data.append('1')
    writer = csv.writer(f,delimiter=';')
    writer.writerow(data)
    writer2 = csv.writer(f2,delimiter=';')
    writer2.writerow(['1',index])