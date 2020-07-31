import axios from 'axios';

import AuthService  from './AuthService';
const API_URL = process.env.REACT_APP_IS_DEV==='true' ? process.env.REACT_APP_AA_LOCATION : 'http://localhost:8030/';

const FI_MOCK = `{
    "ver": "1.0",
    "timestamp": "2018-12-06T11:39:57.153Z",
    "txnid": "e8cc6822-d4bb-4eb1-9e1b-4996fbff8acb",
    "FIDataRange": {
      "from": "2018-12-06T11:39:57.153Z",
      "to": "2019-12-06T11:39:57.153Z"
    },
      "VDRProvider": {
          "vdrpid": "VDRP-14",
          "fiuid": "1",
          "actionid": "2"
      },
    "Consent": {
      "id": "654024c8-29c8-11e8-8868-0289437bf331",
      "digitalSignature": "Digital signature of the consentDetail section in the consent Artefact"
    },
    "KeyMaterial": {
      "cryptoAlg": "ECDHE",
      "curve": "Curve25519",
      "params": "string",
      "DHPublicKey": {
        "expiry": "2018-12-06T11:39:57.153Z",
        "Parameters": "string",
        "KeyValue": "string"
      },
      "Nonce": 0,
      "Signature": "Signature as defined in W3C standards; Base64 encoded"
    }
  }`;

class AaService{
  loadMockFIPData(json){
    let token = AuthService.getCurrentUser().token;
    let config = {
      headers: {
        'client_api_key': token,
      }
    }
    return axios.post(API_URL+'loadData',
      json,config).then(response=>{
      
      return response;
    })
  }

  doMockFIRequest(actionid){
    let token = AuthService.getCurrentUser().token;
    let fiuid = AuthService.getCurrentUser().fiuId;

    let config = {
      headers: {
        'client_api_key': token,
      }
    }
    let jsonData = JSON.parse(FI_MOCK);
    jsonData.VDRProvider.fiuid = fiuid.toString();
    jsonData.VDRProvider.actionid = actionid.toString();
    console.log(JSON.stringify(jsonData));
    return axios.post(API_URL+'FI/request',jsonData,config)
      .then(response => {
        console.log(response)
        return response;
      })
  }
}
  


export default new AaService();