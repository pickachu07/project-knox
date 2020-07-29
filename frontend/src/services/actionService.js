import axios from 'axios';

//import AuthService  from './AuthService';
const API_URL = 'http://localhost:8080/action';

class ActionService {
  
  saveAction(name,runtime,code,main, memory, timeout) {
    
    //let apiKey = AuthService.getCurrentUser().token;
    let config = {
      headers: {
        'vdpr-api-key': '',
      }
    }
             
    return axios
      .post(API_URL + '/create', {
        name,
        runtime,
        code,
        main,
        memory,
        timeout
      },config)
      .then(response => {
        console.log(response);
        return response.data;
      }).catch(error => {
        console.log(error);
        return null;
      })
  }


  //updateAction(id,name,runtime,code,main, memory, timeout) {
    
  //let apiKey = AuthService.getCurrentUser().token;
    
             
   
  //}



}
export default new ActionService();