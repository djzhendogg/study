import requests
import json
import pandas as pd

headers = {
    'accept': 'application/json',
    'Content-Type': 'application/json',
}

sequences = ["CTAG", "ATGC", "AXRT"]
dna_fin_data = pd.DataFrame()
params = {
    'sequences': ', '.join(sequences),
    'polymer_type': 'RNA',
    'encoding_strategy': 'aptamer',
    'skip_unprocessable': 'true',
}

response = requests.post('https://ai-chemistry.itmo.ru/api/encode_sequence', params=params, headers=headers)
assert response.status_code == 200
result = json.loads(response.content)
print(result)