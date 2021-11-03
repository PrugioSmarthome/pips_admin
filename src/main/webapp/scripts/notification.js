function initWebSocketClient(url) {
    var ws = new WebSocket(url);
    var client = Stomp.over(ws);

    return client;
}

function initWebSocket(client, userId, message) {
    client.heartbeat.outgoing = 0;
    client.heartbeat.incoming = 0;
    client.reconnect_delay = 60000;

    client.debug = onDebug;

    client.connect(userId, message, onConnect, onError, "/");

    function onConnect() {
        console.log("Stomp Connect");
        var topicPath = "/topic/" + current_user;

        client.subscribe(topicPath, function(d) {
            console.log("Success " + topicPath);
            console.log(d.body);

            var ND_UM = d.body.slice(-5);
            if(ND_UM == "ND_UM"){
                if(d.body.length > 10){
                    location.reload();
                    alert(d.body.slice(0,-5));
                }else{
                    location.reload();
                }
            }else{
                alert(d.body);
            }
        });
    }

    function onError(e) {
        console.log("Stomp Error", e);

        if (e.includes("Lost connection")) {
            checkSocketConnection();
        }
    }

    function onDebug(d) {
        console.log("Stomp Debug", d);

        if (d.includes("Lost connection")) {
            checkSocketConnection();
        }
    }
}

function disconnectWebSocketClient(client) {
  client.disconnect(function() {
    console.log("Success Stomp Disconnect");
  });
}