
```
curl -X POST http://localhost:5000/api/v1/lineage \
	-H 'Content-Type: application/json' \
	-d @json/startjob.json
```

```
curl -X POST http://localhost:5000/api/v1/lineage \
	-H 'Content-Type: application/json' \
	-d @json/completejob.json
```

```
python3 -m venv .venv
source .venv/bin/activate
pip install -r requirements.txt
```

```
./generate-events.py
```
