import React, {useEffect} from 'react';
import clsx from 'clsx';
import PropTypes from 'prop-types';
import { makeStyles } from '@material-ui/styles';
import { Card, CardContent, Grid, Typography, Avatar } from '@material-ui/core';
import DirectionsRunOutlinedIcon from '@material-ui/icons/DirectionsRunOutlined';
import ActionService from '../../../../services/actionService';
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
    backgroundColor: theme.palette.error.main,
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
    color: theme.palette.error.dark
  },
  differenceValue: {
    color: theme.palette.error.dark,
    marginRight: theme.spacing(1)
  }
}));

const ActionCount = props => {
  const { className, actionCount, ...rest } = props;

  const classes = useStyles();

  const [stats, setStats] = React.useState({
    actionCount:0,
  });

  const updateActionCount = () =>{
    let actionPromise = ActionService.getAllActions();
    actionPromise.then(actionResponse => {
      if(actionResponse.status == 200){
        setStats({
          actionCount:actionResponse.data?actionResponse.data.length:0
        })
      }
    }).catch()
  } 

  useEffect(() => {
    updateActionCount();

  },[]);

  return (
    <Card
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
              TOTAL ACTIONS
            </Typography>
              <Typography variant="h3">{stats.actionCount}</Typography>
          </Grid>
          <Grid item>
            <Avatar className={classes.avatar}>
              <DirectionsRunOutlinedIcon className={classes.icon} />
            </Avatar>
          </Grid>
        </Grid>
      </CardContent>
    </Card>
  );
};

ActionCount.propTypes = {
  className: PropTypes.string
};

export default ActionCount;
