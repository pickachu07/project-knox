//import axios from 'axios';

import AuthService  from './AuthService';
import executionData from './executionData';
const API_URL = 'http://localhost:8080/execution';

class ExecutionService {
  
 
 
  fetchExecutions() {
    return executionData;
  }

}
export default new ExecutionService();