import axios from 'axios';

import AuthService  from './AuthService';
import executionData from './executionData';
const API_URL = 'http://localhost:8080/execution';

class ExecutionService {

  fetchExecutions(actionid) {
    let apiKey = AuthService.getCurrentUser().apikey;
    let token = AuthService.getCurrentUser().token;
    let config = {
      headers: {
        'vdpr_api_key': apiKey,
        Authorization: `Bearer ${token}` 
      }
    }
    return axios.get(API_URL+"/getAll/action/"+actionid,config)
      .then(response =>{
        console.log(response);
        return response;
      })

    //return executionData;
  }

  fetchAllExecutions() {
    let apiKey = AuthService.getCurrentUser().apikey;
    let token = AuthService.getCurrentUser().token;
    let fiuid = AuthService.getCurrentUser().fiuId;
    let config = {
      headers: {
        'vdpr_api_key': apiKey,
        Authorization: `Bearer ${token}` 
      }
    }
    return axios.get(API_URL+"/getAll/fiu/"+fiuid,config)
      .then(response =>{
        console.log(response);
        return response;
      })

    //return executionData;
  }

}
export default new ExecutionService();