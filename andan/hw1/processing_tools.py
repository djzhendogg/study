import pandas as pd
import numpy as np


def make_list_flavor(df, col_name):
    complex_flavor_group = list(df[col_name].unique())
    all_flavor_group = []
    for complex_flavor in complex_flavor_group:
        if pd.notna(complex_flavor):
            flavors = complex_flavor.split(';')
            for flavor in flavors:
                if flavor not in all_flavor_group:
                    all_flavor_group.append(flavor)
    return all_flavor_group


def extract_columns(df, col_name):
    all_flavor_group = make_list_flavor(df, col_name)
    print(all_flavor_group)
    if 'iso e super' in all_flavor_group:
        print(col_name)
        print(all_flavor_group)
        all_flavor_group.remove('iso e super')

    all_flavor_group_dict_cols = {}
    df_col_flavor_group = df[col_name]
    for flavor in all_flavor_group:
        df_col_list = []
        for df_col in df_col_flavor_group:
            if pd.notna(df_col):
                if flavor in df_col:
                    df_col_list.append(1)
                else:
                    df_col_list.append(0)
            else:
                df_col_list.append(0)
        all_flavor_group_dict_cols[flavor] = df_col_list
    return all_flavor_group_dict_cols


def add_flav_columns(df, col_name):
    flavor_group_columns = extract_columns(df, col_name)
    for i in flavor_group_columns.keys():
        df[i + '_f_g'] = flavor_group_columns[i]
    return df


def make_one_flavor_group_type(df):
    a = list(df['flavor_group'])
    z = []
    for i in a:
        if pd.notna(i):
            b = i.split(';')
            to_add = ''
            for j in b:
                if j == 'мускус':
                    to_add += 'мускусные;'
                elif j == 'древесина':
                    to_add += 'древесные;'
                elif j == 'альдегиды':
                    to_add += 'альдегидные;'
                elif j == 'ваниль':
                    to_add += 'ванильные;'
                else:
                    to_add += j + ';'
            z.append(to_add)
        else:
            z.append(np.nan)
    return z
