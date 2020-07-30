import axios from 'axios';

import AuthService  from './AuthService';
const API_URL = 'http://localhost:8080/action';

class ActionService {
  
  saveAction(name,runtime,code,main, memory, timeout) {
    
    let apiKey = AuthService.getCurrentUser().apikey;
    let fiuid  = AuthService.getCurrentUser().fiuId;
    let config = {
      headers: {
        'vdpr_api_key': apiKey,
      }
    }
             
    return axios
      .post(API_URL + '/create', {
        'fiuid':fiuid,
        'actionname':name,
        'runtime':runtime,
        'code':code,
        'main':main,
        'memory':memory,
        'timeout':timeout
      },config)
      .then(response => {
        console.log(response);
        return response;
      })
  }

  updateAction(actionid,name,runtime,code,main, memory, timeout){
    console.log(actionid+' '+name+' '+runtime+' '+code+' '+main+' '+memory+' '+ timeout);
    let apiKey = AuthService.getCurrentUser().apikey;
    let fiuid  = AuthService.getCurrentUser().fiuId;
    let token = AuthService.getCurrentUser().token;
    let config = {
      headers: {
        'vdpr_api_key': apiKey,
        Authorization: `Bearer ${token}` 
      }
    }
    return axios.put(API_URL + '/update',{
      'actionid':actionid,
      'fiuid':fiuid,
      'actionname':name,
      'runtime':runtime,
      'code':code,
      'main':main,
      'memory':memory,
      'timeout':timeout
    },config)
      .then(response =>{
        console.log(response);
        return response;
      })

  }
  

  deleteAction(actionid){
    console.log('deleting action:'+actionid);
    let apiKey = AuthService.getCurrentUser().apikey;
    let token = AuthService.getCurrentUser().token;
    let config = {
      headers: {
        'vdpr_api_key': apiKey,
        Authorization: `Bearer ${token}` 
      }
    }
    return axios.delete(API_URL+ '/delete/' + actionid,config)
      .then(response =>{
        console.log(response);
        return response;
      })
  }

  getAllActions(){
    
    let apiKey = AuthService.getCurrentUser().apikey;
    let fiuid  = AuthService.getCurrentUser().fiuId;
    let token = AuthService.getCurrentUser().token;
    let config = {
      headers: {
        'vdpr_api_key': apiKey,
        Authorization: `Bearer ${token}` 
      }
    }
    return axios.get(API_URL+'/getAll/'+fiuid+'?deleted=false',config)
      .then(response =>{
        console.log(response);
        return response
      })
  }


  getAction(id){
    
    let apiKey = AuthService.getCurrentUser().apikey;
    let token = AuthService.getCurrentUser().token;
    let config = {
      headers: {
        'vdpr-api-key': apiKey,
        Authorization: `Bearer ${token}` 
      }
    }
    return axios.get(API_URL+'/get'+id,config)
      .then(response =>{
        console.log(response);
        return response
      })
  }



}
export default new ActionService();