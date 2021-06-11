import psycopg2
import pandas as pd


def loadTable(connection, fileName, tableName):
    with open(fileName, 'r') as file:
        cursor.copy_from(file, tableName, sep=',')
    connection.commit()
    print("Table " + tableName + " inserted successfully")


def printSep(text):
    print("-------------------------------------------------------------------")
    print("-------------------------------------------------------------------")
    print("                           " + text)
    print("-------------------------------------------------------------------")
    print("-------------------------------------------------------------------")


try:
    #connection = psycopg2.connect(user="doadmin",
    #                             password="xbe4qv0g3ze08p75",
    #                             host="db-clickerperu-pruebas-do-user-7908831-0.b.db.ondigitalocean.com",
    #                             port="25060",
    #                             database="clicker-pruebas-web")

    connection = psycopg2.connect(user="postgres",
                                   password="admin",
                                    host="127.0.0.1",
                                     port="5432",
                                     database="postgres")
    cursor = connection.cursor()
    printSep('accesorios')
    loadTable(connection, 'accesorios.csv', 'accesorios')


    printSep('FINISHED')

except (Exception, psycopg2.Error) as error:
    if connection:
        print("Failed to insert record", error)

finally:
    if connection:
        cursor.close()
        connection.close()
        print("PostgreSQL connection is closed")