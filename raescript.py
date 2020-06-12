import requests
import sys

palabra_a_filtrar = sys.argv[1]

try:
    url = f"https://dle.rae.es/{palabra_a_filtrar.lower()}"
    conn = requests.get(url)
    exists = f"La palabra <b>{palabra_a_filtrar.lower()}</b> no está en el Diccionario." in conn.text
    if exists:
        exit(1)
except:
    exit(1)
