import uuid from 'uuid/v1';

export default [
  {
    id: uuid(),
    fiu_id: uuid(),
    name: 'fetchAvgBalance',
    code: `function main(){
      consoe.log("average balance");
    }`,
    main: 'main',
    timeout: 100,
    memory:128
  },
  {
    id: uuid(),
    fiu_id: uuid(),
    name: 'fetchAvgBalance',
    code: `function start =>(param1,param2) => {
      consoe.log("average balance");
      return null;
    }`,
    main: 'start',
    timeout: 150,
    memory:246  },
  {
    id: uuid(),
    fiu_id: uuid(),
    name:'fetch current account balance',
    code: `function main = (params) => {
      consoe.log("current balance");
    }`,
    main: 'main',
    timeout: 200,
    memory:128
  }
];
