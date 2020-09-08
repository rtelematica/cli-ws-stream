package com.walkoding.cliwsstream;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.nova.orto.cognitiveservice.CognitiveResponse;
import com.nova.orto.cognitiveservice.Indicator;
import com.walkoding.cliwsstream.dto.FlowStreamDTO;
import com.walkoding.cliwsstream.dto.HelloMessageDTO;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.time.LocalDate;

@Controller
public class FlowStepStreamWS {

    private final Logger log = LoggerFactory.getLogger(FlowStepStreamWS.class);

    private final SimpMessagingTemplate simpMessagingTemplate;

    private final String VQ_PORT = "50050";
    private final String PL_PORT = "50051";
    private final String IE_PORT = "50052";
    private final String AE_PORT = "50053";
    private final String FR_PORT = "50054";
    private final String VOS_PORT = "50055";
    private final String AC_PORT = "50056";

    //private static final String PATH = "127.0.0.1";
    private static final String PATH = "ec2-34-217-62-166.us-west-2.compute.amazonaws.com";

    private static final String STREAM_URL = "rtmp://172.31.13.9:1935/live/";

    public FlowStepStreamWS(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/hello")
    //@SendTo("/topic/greetings")
    public void greeting(HelloMessageDTO message) throws Exception {
        simpMessagingTemplate.convertAndSend("/topic/greetings", "Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    @MessageMapping("/flows/streams/steps/_video_quality")
    public void stepVideoQuality(FlowStreamDTO flowStreamDTO) {
        log.debug("REST request to step VideoQuality: {}", flowStreamDTO);


        try {
            Thread.sleep(1000);
            CognitiveResponse response = CognitiveResponse.newBuilder()
                    .setType(CognitiveResponse.Type.INDICATOR)
                    .setIndicator(Indicator.newBuilder().setType(1).setMessage("Starting cognitive process").build()).build();

            log.debug("CS VideoQuality Response: {}", response);
            simpMessagingTemplate.convertAndSend("/topic/video-quality", "Quality Response: " + response);

            Thread.sleep(1000);

            response = CognitiveResponse.newBuilder()
                    .setType(CognitiveResponse.Type.INDICATOR)
                    .setIndicator(Indicator.newBuilder().setType(5).setMessage("Error: Empty source").build()).build();
            log.debug("CS VideoQuality Response: {}", response);
            simpMessagingTemplate.convertAndSend("/topic/video-quality", "Quality Response: " + response);


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @MessageMapping("/flows/streams/steps/_proof_live")
    public void stepProofLive(FlowStreamDTO flowStreamDTO) {
        log.debug("REST request to step ProofLive: {}", flowStreamDTO);

        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

            Thread.sleep(1000);
            CognitiveWSResponse wsRes = new CognitiveWSResponse();
            wsRes.type = CType.INDICATOR;
            wsRes.indicator = new WIndicator();
            wsRes.indicator.type = 1;
            wsRes.indicator.message = "Starting cognitive process";
            String json = ow.writeValueAsString(wsRes);
            System.out.println("CS IdsFrontExtractor Response: " + json);
            simpMessagingTemplate.convertAndSend("/topic/proof-live", "" + json);

            Thread.sleep(1000);
            wsRes = new CognitiveWSResponse();
            wsRes.type = CType.INDICATOR;
            wsRes.indicator = new WIndicator();
            wsRes.indicator.type = 3;
            wsRes.indicator.message = "Start processing of frames";
            json = ow.writeValueAsString(wsRes);
            System.out.println("CS IdsFrontExtractor Response: " + json);
            simpMessagingTemplate.convertAndSend("/topic/proof-live", "" + json);

            Thread.sleep(5000);
            wsRes = new CognitiveWSResponse();
            wsRes.type = CType.DATA;
            wsRes.data = new WData();
            wsRes.data.dataJSON = new DataJSON();
            wsRes.data.dataJSON.success = true;
            json = ow.writeValueAsString(wsRes);
            System.out.println("CS IdsFrontExtractor Response: " + json);
            simpMessagingTemplate.convertAndSend("/topic/proof-live", json);

            Thread.sleep(4000);
            wsRes = new CognitiveWSResponse();
            wsRes.type = CType.INDICATOR;
            wsRes.indicator = new WIndicator();
            wsRes.indicator.type = 6;
            wsRes.indicator.message = "End of cognitive process";
            json = ow.writeValueAsString(wsRes);
            System.out.println("CS IdsFrontExtractor Response: " + json);
            simpMessagingTemplate.convertAndSend("/topic/proof-live", "" + json);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch(JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @MessageMapping("/flows/streams/steps/_address_extractor")
    public void stepAddressExtractor(FlowStreamDTO flowStreamDTO) {
        log.debug("REST request to step AddressExtractor: {}", flowStreamDTO);

        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

            Thread.sleep(1000);
            CognitiveWSResponse wsRes = new CognitiveWSResponse();
            wsRes.type = CType.INDICATOR;
            wsRes.indicator = new WIndicator();
            wsRes.indicator.type = 1;
            wsRes.indicator.message = "Starting cognitive process";
            String json = ow.writeValueAsString(wsRes);
            System.out.println("CS IdsFrontExtractor Response: " + json);
            simpMessagingTemplate.convertAndSend("/topic/address-extractor", "" + json);

            Thread.sleep(1000);
            wsRes = new CognitiveWSResponse();
            wsRes.type = CType.INDICATOR;
            wsRes.indicator = new WIndicator();
            wsRes.indicator.type = 3;
            wsRes.indicator.message = "Start processing of frames";
            json = ow.writeValueAsString(wsRes);
            System.out.println("CS IdsFrontExtractor Response: " + json);
            simpMessagingTemplate.convertAndSend("/topic/address-extractor", "" + json);

            Thread.sleep(5000);
            wsRes = new CognitiveWSResponse();
            wsRes.type = CType.DATA;
            wsRes.data = new WData();
            wsRes.data.dataJSON = new DataJSON();
            wsRes.data.dataJSON.success = true;
            wsRes.data.dataJSON.address = getAddress();
            json = ow.writeValueAsString(wsRes);
            System.out.println("CS IdsFrontExtractor Response: " + json);
            simpMessagingTemplate.convertAndSend("/topic/address-extractor", json);

            Thread.sleep(4000);
            wsRes = new CognitiveWSResponse();
            wsRes.type = CType.INDICATOR;
            wsRes.indicator = new WIndicator();
            wsRes.indicator.type = 6;
            wsRes.indicator.message = "End of cognitive process";
            json = ow.writeValueAsString(wsRes);
            System.out.println("CS IdsFrontExtractor Response: " + json);
            simpMessagingTemplate.convertAndSend("/topic/address-extractor", "" + json);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch(JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private WSAddress getAddress() {
        WSAddress address =  new WSAddress();
        address.streetName = "street";
        address.externalNumber = "123";
        address.internalNumber = "d45";
        address.neighborhood = "condesa";
        address.municipally = "tlalapan";
        address.postalCode = "55140";
        address.city = "cdmx";
        address.state = "state";
        address.city = "méxico";

        return address;
    }

    @MessageMapping("/flows/streams/steps/_ids_front_extractor")
    public void stepIdsFrontExtractor(FlowStreamDTO flowStreamDTO) {
        log.debug("REST request to step IdsFrontExtractor: {}", flowStreamDTO);

        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

            Thread.sleep(1000);
            CognitiveWSResponse wsRes = new CognitiveWSResponse();
            wsRes.type = CType.INDICATOR;
            wsRes.indicator = new WIndicator();
            wsRes.indicator.type = 1;
            wsRes.indicator.message = "Starting cognitive process";
            String json = ow.writeValueAsString(wsRes);
            System.out.println("CS IdsFrontExtractor Response: " + json);
            simpMessagingTemplate.convertAndSend("/topic/ids-front-extractor", "" + json);

//            for(int i = 0; i < 9; i++) {
//                Thread.sleep(1000);
//                wsRes = new CognitiveWSResponse();
//                wsRes.type = CType.INDICATOR;
//                wsRes.indicator = new WIndicator();
//                wsRes.indicator.type = 5;
//                wsRes.indicator.message = "Error: Empty source";
//                json = ow.writeValueAsString(wsRes);
//                System.out.println("CS FaceRecognition Response: " + json);
//                simpMessagingTemplate.convertAndSend("/topic/face-recognition", "" + json);
//            }

            Thread.sleep(1000);
            wsRes = new CognitiveWSResponse();
            wsRes.type = CType.INDICATOR;
            wsRes.indicator = new WIndicator();
            wsRes.indicator.type = 3;
            wsRes.indicator.message = "Start processing of frames";
            json = ow.writeValueAsString(wsRes);
            System.out.println("CS IdsFrontExtractor Response: " + json);
            simpMessagingTemplate.convertAndSend("/topic/ids-front-extractor", "" + json);

            Thread.sleep(5000);
            wsRes = new CognitiveWSResponse();
            wsRes.type = CType.DATA;
            wsRes.data = new WData();
            wsRes.data.dataJSON = new DataJSON();
            wsRes.data.dataJSON.success = true;
            wsRes.data.dataJSON.userData = getUserData();
            json = ow.writeValueAsString(wsRes);
            System.out.println("CS IdsFrontExtractor Response: " + json);
            simpMessagingTemplate.convertAndSend("/topic/ids-front-extractor", json);

            Thread.sleep(4000);
            wsRes = new CognitiveWSResponse();
            wsRes.type = CType.INDICATOR;
            wsRes.indicator = new WIndicator();
            wsRes.indicator.type = 6;
            wsRes.indicator.message = "End of cognitive process";
            json = ow.writeValueAsString(wsRes);
            System.out.println("CS IdsFrontExtractor Response: " + json);
            simpMessagingTemplate.convertAndSend("/topic/ids-front-extractor", "" + json);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch(JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private UserData getUserData() {
        UserData ud = new UserData();
        ud.address =  new UserData.Address();
        ud.customerName = new UserData.CustomerName();
        ud.customerData = new UserData.CustomerData();

        ud.customerName.name = "Example";
        ud.customerName.lastName = "Of";
        ud.customerName.motherLastName = "Websockets";

        ud.address.street = "street";
        ud.address.externalNumber = "12";
        ud.address.internalNumber = "C413";
        ud.address.city = "city";
        ud.address.municipality = "municipality";
        ud.address.neighborhood = "neighborhood";
        ud.address.postalCode = "55140";
        ud.address.state = "cdmx";
        ud.address.country = "méxico";

        ud.customerData.birthDate = LocalDate.now();
        ud.customerData.curp = "GHYT45124578";
        ud.customerData.gender = "M";
        ud.customerData.identificationNumber = "1234567899876";
        ud.customerData.placeBirth = "méxico";

        return ud;
    }

    @MessageMapping("/flows/streams/steps/_ids_back_extractor")
    public void stepIdsBackExtractor(FlowStreamDTO flowStreamDTO) {
        log.debug("REST request to step IdsBackExtractor: {}", flowStreamDTO);

        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

            Thread.sleep(1000);
            CognitiveWSResponse wsRes = new CognitiveWSResponse();
            wsRes.type = CType.INDICATOR;
            wsRes.indicator = new WIndicator();
            wsRes.indicator.type = 1;
            wsRes.indicator.message = "Starting cognitive process";
            String json = ow.writeValueAsString(wsRes);
            System.out.println("CS IdsFrontExtractor Response: " + json);
            simpMessagingTemplate.convertAndSend("/topic/ids-back-extractor", "" + json);

            Thread.sleep(3000);
            wsRes = new CognitiveWSResponse();
            wsRes.type = CType.INDICATOR;
            wsRes.indicator = new WIndicator();
            wsRes.indicator.type = 3;
            wsRes.indicator.message = "Start processing of frames";
            json = ow.writeValueAsString(wsRes);
            System.out.println("CS IdsFrontExtractor Response: " + json);
            simpMessagingTemplate.convertAndSend("/topic/ids-back-extractor", "" + json);

            Thread.sleep(8000);
            wsRes = new CognitiveWSResponse();
            wsRes.type = CType.DATA;
            wsRes.data = new WData();
            wsRes.data.dataJSON = new DataJSON();
            wsRes.data.dataJSON.success = true;
            json = ow.writeValueAsString(wsRes);
            System.out.println("CS IdsFrontExtractor Response: " + json);
            simpMessagingTemplate.convertAndSend("/topic/ids-back-extractor", json);

            Thread.sleep(2000);
            wsRes = new CognitiveWSResponse();
            wsRes.type = CType.INDICATOR;
            wsRes.indicator = new WIndicator();
            wsRes.indicator.type = 6;
            wsRes.indicator.message = "End of cognitive process";
            json = ow.writeValueAsString(wsRes);
            System.out.println("CS IdsFrontExtractor Response: " + json);
            simpMessagingTemplate.convertAndSend("/topic/ids-back-extractor", "" + json);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch(JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @MessageMapping("/flows/streams/steps/_face_recognition")
    public void stepFaceRecognition(FlowStreamDTO flowStreamDTO) {
        log.debug("REST request to step FaceRecognition: {}", flowStreamDTO);

        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

            Thread.sleep(1000);
            CognitiveWSResponse wsRes = new CognitiveWSResponse();
            wsRes.type = CType.INDICATOR;
            wsRes.indicator = new WIndicator();
            wsRes.indicator.type = 1;
            wsRes.indicator.message = "Starting cognitive process";
            String json = ow.writeValueAsString(wsRes);
            System.out.println("CS FaceRecognition Response: " + json);
            simpMessagingTemplate.convertAndSend("/topic/face-recognition", "" + json);

            Thread.sleep(3000);
            wsRes = new CognitiveWSResponse();
            wsRes.type = CType.INDICATOR;
            wsRes.indicator = new WIndicator();
            wsRes.indicator.type = 3;
            wsRes.indicator.message = "Start processing of frames";
            json = ow.writeValueAsString(wsRes);
            System.out.println("CS FaceRecognition Response: " + json);
            simpMessagingTemplate.convertAndSend("/topic/face-recognition", "" + json);

            Thread.sleep(8000);
            wsRes = new CognitiveWSResponse();
            wsRes.type = CType.DATA;
            wsRes.data = new WData();
            wsRes.data.dataJSON = new DataJSON();
            wsRes.data.dataJSON.success = true;
            json = ow.writeValueAsString(wsRes);
            System.out.println("CS FaceRecognition Response: " + json);
            simpMessagingTemplate.convertAndSend("/topic/face-recognition", json);

            Thread.sleep(2000);
            wsRes = new CognitiveWSResponse();
            wsRes.type = CType.INDICATOR;
            wsRes.indicator = new WIndicator();
            wsRes.indicator.type = 6;
            wsRes.indicator.message = "End of cognitive process";
            json = ow.writeValueAsString(wsRes);
            System.out.println("CS FaceRecognition Response: " + json);
            simpMessagingTemplate.convertAndSend("/topic/face-recognition", "" + json);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch(JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @MessageMapping("/flows/streams/steps/_validate_otp_speech")
    public void stepValidateOtpSpeech(FlowStreamDTO flowStreamDTO) {
        log.debug("REST request to step ValidateOtpSpeech: {}", flowStreamDTO);

        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

            Thread.sleep(1000);
            CognitiveWSResponse wsRes = new CognitiveWSResponse();
            wsRes.type = CType.INDICATOR;
            wsRes.indicator = new WIndicator();
            wsRes.indicator.type = 1;
            wsRes.indicator.message = "Starting cognitive process";
            String json = ow.writeValueAsString(wsRes);
            System.out.println("CS IdsFrontExtractor Response: " + json);
            simpMessagingTemplate.convertAndSend("/topic/validate-otp-speech", "" + json);

            Thread.sleep(3000);
            wsRes = new CognitiveWSResponse();
            wsRes.type = CType.INDICATOR;
            wsRes.indicator = new WIndicator();
            wsRes.indicator.type = 3;
            wsRes.indicator.message = "Start processing of frames";
            json = ow.writeValueAsString(wsRes);
            System.out.println("CS IdsFrontExtractor Response: " + json);
            simpMessagingTemplate.convertAndSend("/topic/validate-otp-speech", "" + json);

            Thread.sleep(8000);
            wsRes = new CognitiveWSResponse();
            wsRes.type = CType.DATA;
            wsRes.data = new WData();
            wsRes.data.dataJSON = new DataJSON();
            wsRes.data.dataJSON.success = true;
            json = ow.writeValueAsString(wsRes);
            System.out.println("CS IdsFrontExtractor Response: " + json);
            simpMessagingTemplate.convertAndSend("/topic/validate-otp-speech", json);

            Thread.sleep(2000);
            wsRes = new CognitiveWSResponse();
            wsRes.type = CType.INDICATOR;
            wsRes.indicator = new WIndicator();
            wsRes.indicator.type = 6;
            wsRes.indicator.message = "End of cognitive process";
            json = ow.writeValueAsString(wsRes);
            System.out.println("CS IdsFrontExtractor Response: " + json);
            simpMessagingTemplate.convertAndSend("/topic/validate-otp-speech", "" + json);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch(JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @MessageMapping("/flows/streams/steps/_accept_contract")
    public void stepAcceptContract(FlowStreamDTO flowStreamDTO) {
        log.debug("REST request to step AcceptContract: {}", flowStreamDTO);

        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

            Thread.sleep(1000);
            CognitiveWSResponse wsRes = new CognitiveWSResponse();
            wsRes.type = CType.INDICATOR;
            wsRes.indicator = new WIndicator();
            wsRes.indicator.type = 1;
            wsRes.indicator.message = "Starting cognitive process";
            String json = ow.writeValueAsString(wsRes);
            System.out.println("CS IdsFrontExtractor Response: " + json);
            simpMessagingTemplate.convertAndSend("/topic/accept-contract", "" + json);

            Thread.sleep(3000);
            wsRes = new CognitiveWSResponse();
            wsRes.type = CType.INDICATOR;
            wsRes.indicator = new WIndicator();
            wsRes.indicator.type = 3;
            wsRes.indicator.message = "Start processing of frames";
            json = ow.writeValueAsString(wsRes);
            System.out.println("CS IdsFrontExtractor Response: " + json);
            simpMessagingTemplate.convertAndSend("/topic/accept-contract", "" + json);

            Thread.sleep(8000);
            wsRes = new CognitiveWSResponse();
            wsRes.type = CType.DATA;
            wsRes.data = new WData();
            wsRes.data.dataJSON = new DataJSON();
            wsRes.data.dataJSON.success = true;
            json = ow.writeValueAsString(wsRes);
            System.out.println("CS IdsFrontExtractor Response: " + json);
            simpMessagingTemplate.convertAndSend("/topic/accept-contract", json);

            Thread.sleep(2000);
            wsRes = new CognitiveWSResponse();
            wsRes.type = CType.INDICATOR;
            wsRes.indicator = new WIndicator();
            wsRes.indicator.type = 6;
            wsRes.indicator.message = "End of cognitive process";
            json = ow.writeValueAsString(wsRes);
            System.out.println("CS IdsFrontExtractor Response: " + json);
            simpMessagingTemplate.convertAndSend("/topic/accept-contract", "" + json);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch(JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public enum CType {
        DATA,
        LOG,
        INDICATOR;
    }

    public static class CognitiveWSResponse {

        public CType type;
        public WIndicator indicator;
        public WData data;
    }

    public static class WIndicator {
        public int type;
        public String message;
    }

    public static class WData {
        public DataJSON dataJSON;
    }

    public static class DataJSON {
        public boolean success;
        public UserData userData;
        public WSAddress address;
    }

    public static class UserData {

        public CustomerName customerName;

        public Address address;

        public CustomerData customerData;

        public static class CustomerName {


            public String name;

            public String lastName;

            public String motherLastName;
        }


        public static class Address {

            public String street;

            public String externalNumber;

            public String internalNumber;

            public String neighborhood;

            public String postalCode;

            public String municipality;

            public String city;

            public String state;

            public String country;
        }


        public static class CustomerData {

            public LocalDate birthDate;

            public String  placeBirth;

            public String gender;

            public String curp;

            public String identificationNumber;
        }

    }

    public static class WSAddress {

        public String streetName;

        public String externalNumber;

        public String internalNumber;

        public String neighborhood;

        public String postalCode;

        public String municipally;

        public String city;

        public String state;

        public String country;
    }
}
