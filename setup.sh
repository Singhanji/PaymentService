#!/bin/bash

APP_DOCKERFILE=Dockerfile-app
APP_IMAGE=sbapp
APP_CONTAINER=myapp
DB_CONTAINER=paymentdb



help(){
  echo "Usage"
  echo " $0 {help|cleanup_app|cleanup_db|mysql|build|run}"
  echo ""
  echo "Commands:"
  echo "help            Show this help message"
  echo "cleanup_app     Stop and remove the app container"
  echo "cleanup_db      Stop and remove the database container"
  echo "my_sql          Run the mysql database container"
  echo "build           Build the payment app docker image"
  echo "run             Run the payment app container"
}

cleanup_DB(){
	podman stop $DB_CONTAINER
	podman rm $DB_CONTAINER --force
}

cleanup_APP(){
	podman stop $APP_CONTAINER
	podman rm $APP_CONTAINER --force
}

mysql(){
  podman run -d --name $DB_CONTAINER -p 3306:3306 \
    -e MYSQL_DATABASE=paymentservice12april \
    -e MYSQL_USER=$MYSQL_USER \
    -e MYSQL_PASSWORD=$MYSQL_PASSWORD \
    -e MYSQL_ROOT_PASSWORD=$MYSQL_ROOT_PASSWORD \
    docker.io/library/mysql:8.0

	sleep 5

	# shellcheck disable=SC2059
	printf "\nINFO: Connect to db using this command:\n -> mysql -u aether -h $(ip a s wlo1|grep -E "^\s+inet\s"|awk '{print $2}'|cut -d'/' -f1) -p\n"
}

paymentiamge_build(){
  #DBHOST must be: FQDN:3306
  podman build -t $APP_IMAGE -f $APP_DOCKERFILE
#    --build-arg RAZORPAY_KEY_ID=$RAZORPAY_KEY_ID \
#    --build-arg RAZORPAY_KEY_SECRET=$RAZORPAY_KEY_SECRET \
#    --build-arg STRIPE_KEY_SECRET=$STRIPE_KEY_SECRET \
#    --build-arg DBHOST=$DBHOST \
#    --build-arg DBUSER=$MYSQL_USER \
#    --build-arg DBPASSWORD=$MYSQL_PASSWORD
}

paymentapp(){
  podman run -d -p 8080:8080 --name $APP_CONTAINER \
    -e RAZORPAY_KEY_ID=$RAZORPAY_KEY_ID \
    -e RAZORPAY_KEY_SECRET=$RAZORPAY_KEY_SECRET \
    -e STRIPE_KEY_SECRET=$STRIPE_KEY_SECRET \
    -e DBHOST=$DBHOST \
    -e DBUSER=$MYSQL_USER \
    -e DBPASSWORD=$MYSQL_PASSWORD \
    $APP_IMAGE:latest
}

if [ -z "$1" ];then
#	printf "\n-> Expecting value in first argument. Supported values: \nmysql \ncleanup \nbuild \nrun \n"
  # shellcheck disable=SC2059
  printf "\n USE '$0 help' TO CHECK ALL THE POSSIBLE VALUES: \n"
	exit 1
fi

#[[ $1 == "help" ]] && help
#[[ $1 == "mysql" ]] && mysql
#[[ $1 == "cleanup_app" ]] && cleanup_app
#[[ $1 == "cleanup_db" ]] && cleanup_DB
#[[ $1 == "build" ]] && paymentiamge_build
#[[ $1 == "app" ]] && paymentapp

case $1 in
help)
  help
  ;;
cleanup_db)
  	cleanup_DB
  	;;
cleanup_app)
  	cleanup_APP
  	;;
 my_sql)
   mysql
    ;;
build)
     paymentiamge_build
  ;;
run)
    paymentapp
  ;;
*)
  echo -n "Invalid option."
  exit 1
  ;;
esac












