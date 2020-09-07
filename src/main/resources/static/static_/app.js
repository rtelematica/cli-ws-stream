var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            //showGreeting(JSON.parse(greeting.body).content);
            showGreeting(greeting.body);
        });
        stompClient.subscribe('/topic/video-quality', function (videoQuality) {
          showVideoQuality(videoQuality.body);
        });
        stompClient.subscribe('/topic/proof-live', function (proofLive) {
          console.log(proofLive.body)
          showProofLive(proofLive.body);
        });
        stompClient.subscribe('/topic/ids-front-extractor', function (idsFrontExtractor) {
          showIdsFrontExtractor(idsFrontExtractor.body);
        });
        stompClient.subscribe('/topic/validate-otp-speech', function (validateOptSpeech) {
          showValidateOtpSpeech(validateOptSpeech.body);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
  stompClient.send("/api/ws/hello", {}, JSON.stringify({'name': $("#name").val()}));
}

function sendVideoQuality() {
  stompClient.send("/api/ws/flows/streams/steps/_video_quality", {}, JSON.stringify({'flowId': "1234", "streamId": $("#vqStreamId").val()}));
}

function sendProofLive() {
    stompClient.send("/api/ws/flows/streams/steps/_proof_live", {}, JSON.stringify({'flowId': "1234", "streamId": $("#plStreamId").val()}));
}

function sendIdsFrontExtractor() {
  stompClient.send("/api/ws/flows/streams/steps/_ids_front_extractor", {}, JSON.stringify({'flowId': "1234", "streamId": $("#ifeStreamId").val()}));
}

function sendValidateOtpSpeech() {
  stompClient.send("/api/ws/flows/streams/steps/_validate_otp_speech", {}, JSON.stringify({'flowId': "1234", "streamId": $("#vosStreamId").val()}));
}


function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

function showVideoQuality(message) {
  $("#vqResponse").append("<tr><td>" + message + "</td></tr>");
}

function showProofLive(message) {
  $("#plResponse").append("<tr><td>" + message + "</td></tr>");
}

function showIdsFrontExtractor(message) {
  $("#ifeResponse").append("<tr><td>" + message + "</td></tr>");
}

function showValidateOtpSpeech(message) {
  $("#vosResponse").append("<tr><td>" + message + "</td></tr>");
}


$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
    $( "#sendProofLive" ).click(function() { sendProofLive(); });
    $( "#sendVideoQuality" ).click(function() { sendVideoQuality(); });
    $( "#sendIdsFrontExtractor" ).click(function() { sendIdsFrontExtractor(); });
    $( "#sendValidateOtpSpeech" ).click(function() { sendValidateOtpSpeech(); });
});

