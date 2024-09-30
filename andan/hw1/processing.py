import pandas as pd
from processing_tools import (
    add_flav_columns,
    make_list_flavor
)


df = pd.read_csv('perfume.tsv',  sep='\t')

# парсинг делался по компании и названию, следовательно эти строки полностью фиктивны
df.drop(df[df['company'].isnull()].index, inplace=True)
df.drop(df[df['name'].isnull()].index, inplace=True)
# если неизвестно чем пахнут, то не на чем предсказывать
df.drop(df[df['flavor_group'].isnull()].index, inplace=True)
# сновные фичи для предсказания, если нул, то строки фиктивны
df.drop(df[df['upper_notes'].isnull() & df['heart_notes'].isnull() & df['base_notes'].isnull()].index, inplace=True)

# заполнение пропусков
df['year'].fillna((df['year'].mean()), inplace=True)
df['expiration, mounth'].fillna((df['expiration, mounth'].mean()), inplace=True)
df['manufacturer'].fillna('no', inplace=True)
df['country'].fillna('no', inplace=True)

add_flav_columns(df, 'flavor_group')

complex_upper_notes = make_list_flavor(df, 'upper_notes')
complex_heart_notes = make_list_flavor(df, 'heart_notes')
complex_base_notes = make_list_flavor(df, 'base_notes')

all_notes = list(set(complex_upper_notes+complex_heart_notes+complex_base_notes))

all_flavor_group_dict_cols = {}
# df_col_flavor_group = df['upper_notes']
for flavor in all_notes:
    df_col_list = []
    for _id in range(df.shape[0]):
        line = df.iloc[_id]
        upper_note = line['upper_notes']
        heart_note = line['heart_notes']
        base_note = line['base_notes']
        if pd.notna(upper_note) and flavor in upper_note:
            df_col_list.append(3)
        elif pd.notna(heart_note) and flavor in heart_note:
            df_col_list.append(2)
        elif pd.notna(base_note) and flavor in base_note:
            df_col_list.append(1)
        else:
            df_col_list.append(0)

    all_flavor_group_dict_cols[flavor] = df_col_list

all_flavor_group_df = pd.DataFrame.from_dict(all_flavor_group_dict_cols)

df = pd.concat([df, all_flavor_group_df], axis=1)

df.drop(['upper_notes', 'heart_notes', 'base_notes', 'flavor_group'], axis=1, inplace=True)

df.to_csv('perfume_processed.csv')
