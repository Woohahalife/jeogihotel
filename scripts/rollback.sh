CURRENT_SERVER=$(grep -oP '(?<=proxy_pass http://127.0.0.1:)\d+' /etc/nginx/nginx.conf | head -n1)
DEFAULT_CONF=" /etc/nginx/nginx.conf"

if [ "$CURRENT_SERVER" = "8083" ];then

  echo "server rollback"
  sudo cp /etc/nginx/nginx.dev1.conf $DEFAULT_CONF
  sudo nginx -s reload

  if [ "$CURRENT_SERVER" = "8082" ]; then
          echo "health check success: $CURRENT_SERVER"
  fi

else
  echo "server rollback"
    sudo cp /etc/nginx/nginx.dev2.conf $DEFAULT_CONF
    sudo nginx -s reload
    if [ "$CURRENT_SERVER" = "8083" ]; then
            echo "health check success: $CURRENT_SERVER"
    fi

fi