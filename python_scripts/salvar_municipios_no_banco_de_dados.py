import pandas as pd
from sqlalchemy import create_engine

file_path = '/tabelas-municipios.xls'


df = pd.read_excel(file_path)

print(df.columns)

# Exibir as primeiras linhas para verificação
print(df.head())

# engine = create_engine('postgresql+psycopg2://postgres:160651@localhost:5432/tcc_reddit')
#
# table_name = 'municipios'
#
# # Salvar o DataFrame no banco de dados
# df.to_sql(table_name, engine, if_exists='append', index=False)
#
# print('Dados importados com sucesso!')
