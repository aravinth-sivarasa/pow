
~~~ sh
gcloud auth application-default login --no-launch-browser

gcloud config set project <project-id>

export GOOGLE_APPLICATION_CREDENTIALS="/home/<user>/.config/gcloud/application_default_credentials.json"

gcloud spanner databases create --instance=three-ack-poc --database-dialect=POSTGRESQL user-db


wget https://storage.googleapis.com/pgadapter-jar-releases/pgadapter.tar.gz && tar -xzvf pgadapter.tar.gz
    
psql -h localhost -p 5432
~~~ 
