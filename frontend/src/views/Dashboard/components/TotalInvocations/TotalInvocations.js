import React,{useEffect} from 'react';
import clsx from 'clsx';
import PropTypes from 'prop-types';
import { makeStyles } from '@material-ui/styles';
import { Card, CardContent, Grid, Typography, Avatar } from '@material-ui/core';
import OfflineBoltOutlinedIcon from '@material-ui/icons/OfflineBoltOutlined';

import ExecutionService from '../../../../services/executionService';

const useStyles = makeStyles(theme => ({
  root: {
    height: '100%'
  },
  content: {
    alignItems: 'center',
    display: 'flex'
  },
  title: {
    fontWeight: 700
  },
  avatar: {
    backgroundColor: theme.palette.success.main,
    height: 56,
    width: 56
  },
  icon: {
    height: 32,
    width: 32
  },
  difference: {
    marginTop: theme.spacing(2),
    display: 'flex',
    alignItems: 'center'
  },
  differenceIcon: {
    color: theme.palette.success.dark
  },
  differenceValue: {
    color: theme.palette.success.dark,
    marginRight: theme.spacing(1)
  }
}));

const TotalInvocations = props => {
  const { className, ...rest } = props;

  const classes = useStyles();

  const [stats, setStats] = React.useState({
    invocationCount:0,
  });


  const updateExecututionCount = () =>{
    let executionPromise = ExecutionService.fetchAllExecutions();
    executionPromise.then(response => {
      if(response.status==200){
        console.log('executions');
        //console.log(response.data);

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

  useEffect(() => {
    updateExecututionCount();

  },[]);


  return (
    <Card
      {...rest}
      className={clsx(classes.root, className)}
    >
      <CardContent>
        <Grid
          container
          justify="space-between"
        >
          <Grid item>
            <Typography
              className={classes.title}
              color="textSecondary"
              gutterBottom
              variant="body2"
            >
              ACTION INVOCATIONS
            </Typography>
            <Typography variant="h3">{stats.invocationCount}</Typography>
          </Grid>
          <Grid item>
            <Avatar className={classes.avatar}>
              <OfflineBoltOutlinedIcon className={classes.icon} />
            </Avatar>
          </Grid>
        </Grid>
      </CardContent>
    </Card>
  );
};

TotalInvocations.propTypes = {
  className: PropTypes.string
};

export default TotalInvocations;
