CURRENT_SERVER=$(grep -oP '(?<=proxy_pass http://127.0.0.1:)\d+' /etc/nginx/nginx.conf | head -n1)
DEFAULT_CONF="/etc/nginx/nginx.conf"
MAX_RETRIES=5
RETRY_INTERVAL=3

health_check() {
  local port="$1"
  for ((i=1; i<=$MAX_RETRIES; i++)); do
    echo "Health check attempt $i..."
    if REQUEST=$(curl -m 5 http://127.0.0.1:$port); then
      echo "Health check success: $CURRENT_SERVER"
      return 0
    else
      if [ "$i" -eq "$MAX_RETRIES" ]; then
        echo "Health check failed after $MAX_RETRIES attempts."
        exit 1
      else
        sleep $RETRY_INTERVAL
      fi
    fi
  done
}

if [ "$CURRENT_SERVER" = "8083" ]; then
  echo "Server rollback: Dev1"
  NEW_SERVER_PORT="8082"
else
  echo "Server rollback: Dev2"
  NEW_SERVER_PORT="8083"
fi

sudo cp /etc/nginx/nginx.dev"$CURRENT_SERVER".conf $DEFAULT_CONF
sudo nginx -s reload

health_check "$NEW_SERVER_PORT"