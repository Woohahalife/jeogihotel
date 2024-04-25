IS_DEV1=$(docker ps | grep accommodation-dev1)
IS_DEV2=$(docker ps | grep accommodation-dev2)
CURRENT_SERVER=$(grep -oP '(?<=proxy_pass http://127.0.0.1:)\d+' /etc/nginx/nginx.conf | head -n1)
DEFAULT_CONF=" /etc/nginx/nginx.conf"

if [ "$CURRENT_SERVER" = "8083" -o -z "$IS_DEV1" ];then # dev2라면 or 첫 배포라면 (환경변수로 설정한 문자열 길이가 0인 경우 -z)

  if [ -n "$IS_DEV1" ];then
    echo "down old container"
    docker-compose stop accommodation-dev1
    docker-compose docker-compose rm -f accommodation-dev1 # 신버전 반영 위해 기존 컨테이너 삭제
  fi

  echo "##### dev2 => dev1 #####"

  echo "1. get green image"
  docker-compose pull accommodation-dev1 # dev1으로 이미지를 내려받아옴

  echo "2. green container up"
  docker-compose up -d accommodation-dev1 # dev1 컨테이너 실행

  counter=0
  while [ 1 = 1 ]; do
  echo "3. green health check..."
  ((counter++))
  sleep 3

  REQUEST=$(curl http://127.0.0.1:8082) # dev1으로 request
    if [ -n "$REQUEST" ]; then # 서비스 가능하면 health check 중지 (문자열 길이가 0보다 큰지 판단 -n)
            echo "health check success"
            echo "Number of attempts: $counter"
            break ;
            fi
  done;

  echo "4. reload nginx"
  sudo cp /etc/nginx/nginx.dev1.conf $DEFAULT_CONF
  sudo nginx -s reload

#  echo "5. blue container down"
#  docker-compose stop accommodation-dev2

else # dev2 운영중
  if [ -n $IS_DEV2 ];then
      echo "down blue container"
      docker-compose stop accommodation-dev2
      docker-compose docker-compose rm -f accommodation-dev2 # 신버전 반영 위해 기존 컨테이너 삭제
  fi
  echo "### dev1 => dev2 ###"

  echo "1. get blue image"
  docker-compose pull accommodation-dev2

  echo "2. blue container up"
  docker-compose up -d accommodation-dev2


  counter=0
  while [ 1 = 1 ]; do
    echo "3. blue health check..."
    ((counter++))
    sleep 3
    REQUEST=$(curl http://127.0.0.1:8083) # blue로 request

    if [ -n "$REQUEST" ]; then # 서비스 가능하면 health check 중지 (문자열 길이가 0보다 큰지 판단 -n)
      echo "health check success"
      echo "Number of attempts: $counter"
      break ;
    fi
  done;

  echo "4. reload nginx" 
  sudo cp /etc/nginx/nginx.dev2.conf $DEFAULT_CONF
  sudo nginx -s reload

#  echo "5. green container down"
#  docker-compose stop accommodation-dev1
fi

