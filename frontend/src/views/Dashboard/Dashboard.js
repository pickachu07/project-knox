import React, {useEffect} from 'react';
import { makeStyles } from '@material-ui/styles';
import { Grid } from '@material-ui/core';
import ActionService from '../../services/actionService';
import ExecutionService from '../../services/executionService';
import {
  ActionCount,
  TotalInvocations,
  ExecutionTime,
  Actions,
} from './components';


const useStyles = makeStyles(theme => ({
  root: {
    padding: theme.spacing(4)
  }
}));

const Dashboard = () => {
  const classes = useStyles();

  const [stats, setStats] = React.useState({
    actionCount:7,
    invocationCount:121,
    executionTime:101
  });

  const updateExecututionCount = () =>{
    let executionPromise = ExecutionService.fetchAllExecutions();
    executionPromise.then(response => {
      if(response.status==200){
        console.log('executions');
        console.log(response.data);

        setStats({
          ...stats,
          invocationCount:response.data.length?response.data.length:150
        });
      }else{
        console.log('data failure:')
      }
    }).catch(error =>{
      console.log('error while fetching executions'+error);
    })
  }

  const updateActionCount = () =>{
    let actionPromise = ActionService.getAllActions();
    actionPromise.then(actionResponse => {
      if(actionResponse.status == 200){
        setStats({
          ...stats,
          actionCount:actionResponse.data?actionResponse.data.length:7
        })
      }
    }).catch()
  } 



  useEffect(() => {
    updateExecututionCount();
    updateActionCount();

  }, []);


  return (
    <div className={classes.root}>
      <Grid
        container
        spacing={4}
      >
        <Grid
          item
          lg={4}
          sm={6}
          xl={4}
          xs={12}
        >
          <ActionCount actionCount={stats.actionCount}/>
        </Grid>
        <Grid
          item
          lg={4}
          sm={6}
          xl={4}
          xs={12}
        >
          <TotalInvocations invocationCount={stats.invocationCount}/>
        </Grid>
        <Grid
          item
          lg={4}
          sm={6}
          xl={4}
          xs={12}
        >
          <ExecutionTime executionTime={stats.executionTime}/>
        </Grid>
        {/* <Grid
          item
          lg={3}
          sm={6}
          xl={3}
          xs={12}
        >
          <TotalProfit />
        </Grid> */}
        <Grid
          item
          lg={12}
          md={12}
          xl={12}
          xs={12}
        >
          <Actions />
        </Grid>
        {/* <Grid
          item
          lg={12}
          md={12}
          xl={12}
          xs={12}
        >
          <ActionList />
        </Grid> */}
        {/* <Grid
          item
          lg={4}
          md={6}
          xl={3}
          xs={12}
        >
          <UsersByDevice />
        </Grid> */}
        {/* <Grid
          item
          lg={4}
          md={6}
          xl={3}
          xs={12}
        >
          <LatestProducts />
        </Grid>
        <Grid
          item
          lg={8}
          md={12}
          xl={9}
          xs={12}
        >
          <LatestOrders />
        </Grid> */}
      </Grid>
    </div>
  );
};

export default Dashboard;
