package com.walkoding.cliwsstream.grpc.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.protobuf.ByteString;
import com.nova.orto.cognitiveservice.*;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class CognitiveServer {

    private static final Logger logger = Logger.getLogger(CognitiveServer.class.getName());

    private final int port;

    private final Server server;


    public CognitiveServer(int port, FlowType flowType) {
        this.port = port;
        server = ServerBuilder.forPort(port).addService(new CognitiveService(flowType))
                .build();
    }

    public void start() throws IOException {
        server.start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            try {
                CognitiveServer.this.stop();
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
            System.err.println("*** server shut down");
        }));
    }

    public void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws Exception {
        CognitiveServer proofLiveServer = new CognitiveServer(50051, FlowType.PROOF_LIVE);
        CognitiveServer faceRecognitionServer = new CognitiveServer(50052, FlowType.FACE_RECOGNITION);
        CognitiveServer idsFrontServer = new CognitiveServer(50053, FlowType.IDS_FRONT_EXTRACTOR);
        CognitiveServer idsBackServer = new CognitiveServer(50054, FlowType.IDS_BACK_EXTRACTOR);
        CognitiveServer validateOtpSpeechServer = new CognitiveServer(50055, FlowType.VALIDATE_SPEECH_TEXT);
        CognitiveServer acceptContractServer = new CognitiveServer(50056, FlowType.ACCEPT_CONTRACT);
        CognitiveServer addressExtractorServer = new CognitiveServer(50057, FlowType.ADDRESS_EXTRACTOR);

        proofLiveServer.start();
        faceRecognitionServer.start();
        idsFrontServer.start();
        idsBackServer.start();
        validateOtpSpeechServer.start();
        acceptContractServer.start();
        addressExtractorServer.start();

        proofLiveServer.blockUntilShutdown();
        faceRecognitionServer.blockUntilShutdown();
        idsFrontServer.blockUntilShutdown();
        idsBackServer.blockUntilShutdown();
        validateOtpSpeechServer.blockUntilShutdown();
        acceptContractServer.blockUntilShutdown();
        addressExtractorServer.blockUntilShutdown();
    }

    public static class CognitiveService extends CognitiveServiceGrpc.CognitiveServiceImplBase {

        private final FlowType flowType;

        public CognitiveService(FlowType flowType) {
            this.flowType = flowType;
        }

        @Override
        public void process(CognitiveRequest request, StreamObserver<CognitiveResponse> responseObserver) {

            List<CognitiveResponse> cognitiveFlow;
            switch (flowType) {

                case IDS_FRONT_EXTRACTOR:
                    cognitiveFlow = genericFlow(getIdsFrontData(true));
                    break;
                case IDS_BACK_EXTRACTOR:
                    cognitiveFlow = genericFlow(getIdsBackData(true));
                    break;
                case PROOF_LIVE:
                    cognitiveFlow = genericFlow(getProofLiveData(true));
                    break;
                case FACE_RECOGNITION:
                    cognitiveFlow = genericFlow(getFaceRecognitionData());
                    break;
                case VALIDATE_SPEECH_TEXT:
                    cognitiveFlow = genericFlow(getSpeechTextData());
                    break;
                case ACCEPT_CONTRACT:
                    cognitiveFlow = genericFlow(getAcceptContractData());
                    break;
                case ADDRESS_EXTRACTOR:
                    cognitiveFlow = genericFlow(getAddressExtractorData());
                    break;
                default:
                    cognitiveFlow = genericFlow(getGenericData(true));
            }

            for (CognitiveResponse cognitiveResponse : cognitiveFlow) {
                try { Thread.sleep( randomBetweenInt(1, 10) * 1000); } catch (InterruptedException e) {e.printStackTrace();}
                responseObserver.onNext(cognitiveResponse);
            }
            responseObserver.onCompleted();
        }

        public List<CognitiveResponse> genericFlow(Data data) {
            CognitiveResponse starting = getIndicator(IndicatorType.STARTING_COGNITIVE_PROCESS);
            CognitiveResponse processing = getIndicator(IndicatorType.START_PROCESSING_FRAMES);
            CognitiveResponse cognitiveData = getCognitiveData(data);
            CognitiveResponse ending = getIndicator(IndicatorType.END_COGNITIVE_PROCESS);
            return Arrays.asList(starting, processing, cognitiveData, ending);
        }

        private CognitiveResponse getIndicator(IndicatorType indicatorType) {
            return CognitiveResponse.newBuilder()
                    .setType(CognitiveResponse.Type.INDICATOR)
                    .setIndicator(Indicator.newBuilder()
                            .setType(indicatorType.getCode())
                            .setMessage(indicatorType.getMessage())
                            .build())
                    .build();
        }

        private CognitiveResponse getCognitiveData(Data data) {
            return CognitiveResponse.newBuilder()
                    .setType(CognitiveResponse.Type.DATA)
                    .setData(data)
                    .build();
        }

        private Data getGenericData(boolean success) {
            DataJson dataJson = new DataJson();
            dataJson.success = success;
            Data data = Data.newBuilder()
                    .setDataJSON(toJson(dataJson))
                    .addMapEntry(Entry.newBuilder()
                            .setKey("Document")
                            .addValue(ByteString.copyFromUtf8("Document"))
                            .build()
                    ).build();
            return data;
        }

        private Data getIdsFrontData(boolean success) {
            IdsFrontDataJson dataJson = getIdsFrontDataJsonRandom(success);
            Data data = Data.newBuilder()
                    .setDataJSON(toJson(dataJson))
                    .addMapEntry(Entry.newBuilder()
                            .setKey("Document")
                            .addValue(ByteString.copyFrom(getImage("ine_front")))
                            .build()
                    ).build();
            return data;
        }

        private Data getIdsBackData(boolean success) {
            DataJson dataJson = new DataJson();
            dataJson.success = success;
            Data data = Data.newBuilder()
                    .setDataJSON(toJson(dataJson))
                    .addMapEntry(Entry.newBuilder()
                            .setKey("Document")
                            .addValue(ByteString.copyFrom(getImage("ine_back")))
                            .build()
                    ).build();
            return data;
        }

        private Data getProofLiveData(boolean success) {
            ProofLiveDataJson dataJson = new ProofLiveDataJson();
            dataJson.validation = success;
            Data data = Data.newBuilder()
                    .setDataJSON(toJson(dataJson))
                    .addMapEntry(Entry.newBuilder()
                            .setKey("Sample_frame")
                            .addValue(ByteString.copyFrom(getImage("rostro")))
                            .build()
                    ).build();
            return data;
        }

        private Data getFaceRecognitionData() {
            FaceRecognitionDataJson dataJson = new FaceRecognitionDataJson();
            dataJson.coincidence = randomBetweenInt(1, 100) / 100F * 100;
            System.out.println("Coincidence: " + dataJson.coincidence);
            Data data = Data.newBuilder()
                    .setDataJSON(toJson(dataJson))
                    .addMapEntry(Entry.newBuilder()
                            .setKey("Target")
                            .addValue(ByteString.copyFrom(getImage("parpadeo")))
                            .build())
                    .addMapEntry(Entry.newBuilder()
                            .setKey("Input")
                            .addValue(ByteString.copyFrom(getImage("sonrisa")))
                            .build())
                    .build();
            return data;
        }

        private Data getSpeechTextData() {
            SpeechTextDataJson dataJson = new SpeechTextDataJson();
            dataJson.transcript = "Este es un texto corto";
            Data data = Data.newBuilder()
                    .setDataJSON(toJson(dataJson))
                    .addMapEntry(Entry.newBuilder()
                            .setKey("audio")
                            .addValue(ByteString.copyFrom(getAudio()))
                            .build())
                    .build();
            return data;
        }
        
        private byte[] getImage(String name) {
        	
        	name = "src/main/resources/"+name+".jpg";
        	File file = new File(name);
        	 
        	try {
				return Files.readAllBytes(file.toPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
        }
        
        private byte[] getAudio() {
        	
        	File file = new File("src/main/resources/audio.mp3");
        	 
        	try {
				return Files.readAllBytes(file.toPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
        }
        private Data getAcceptContractData() {
            SpeechTextDataJson dataJson = new SpeechTextDataJson();
            dataJson.transcript = "Acepto el contracto";
            Data data = Data.newBuilder()
                    .setDataJSON(toJson(dataJson))
                    .addMapEntry(Entry.newBuilder()
                            .setKey("audio")
                            .addValue(ByteString.copyFrom(getAudio()))
                            .build())
                    .build();
            return data;
        }

        private Data getAddressExtractorData() {
            AddressExtractorDataJson dataJson = new AddressExtractorDataJson();
            dataJson.address = "Javiro 28 Rinconada de Aragon 55140 Ecatepec de Morelos, Méx.";
            Data data = Data.newBuilder()
                    .setDataJSON(toJson(dataJson))
                    .addMapEntry(Entry.newBuilder()
                            .setKey("Image")
                            .addValue(ByteString.copyFrom(getImage("address")))
                            .build())
                    .build();
            return data;
        }

        private IdsFrontDataJson getIdsFrontDataJsonRandom(boolean success) {
            IdsFrontDataJson dataJson =  new IdsFrontDataJson();
            dataJson.success = success;
            dataJson.curp = "ABCDEFGHIJKL" + randomBetweenInt(1, 100000);
            dataJson.address = "Javiro 28 Rinconada de Aragon 55140 Ecatepec de Morelos, Méx.";
            dataJson.birth_date = "20/02/1991";
            dataJson.id = "" + randomBetweenInt(100000, 999999);
            dataJson.register_date = "20/06/1988";
            dataJson.name = "name" + randomBetweenInt(1, 100000);
            dataJson.last_name = "lastname" + randomBetweenInt(1, 100000);
            return dataJson;
        }

        private int randomBetweenInt(int min, int max) {
            return ThreadLocalRandom.current().nextInt(min, max + 1);
        }

        private String toJson(Object obj) {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            try {
                return ow.writeValueAsString(obj);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return "";
            }
        }

        @lombok.Data
        public static class DataJson {
            private boolean success;

        }

        @lombok.Data
        public static class IdsFrontDataJson {
            private boolean success;
            private String oid_class;
            private String name;
            private String last_name;
            private String address;
            private String curp;
            private String id;
            private String register_date;
            private String birth_date;
        }

        @lombok.Data
        public static class ProofLiveDataJson {
            @JsonProperty("Validation")
            private boolean validation;
        }

        @lombok.Data
        public static class FaceRecognitionDataJson {

            @JsonProperty("Coincidence")
            private float coincidence;
        }

        @lombok.Data
        public static class SpeechTextDataJson {
            @JsonProperty("Transcript")
            private String transcript;
        }

        @lombok.Data
        public static class AddressExtractorDataJson {

            @JsonProperty("Address")
            private String address;
        }
    }
}
