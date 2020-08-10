import React, { useState, useEffect  } from 'react';
import clsx from 'clsx';
import PerfectScrollbar from 'react-perfect-scrollbar';
import PropTypes from 'prop-types';
import { makeStyles } from '@material-ui/styles';
import {
  Card,
  CardHeader,
  CardContent,
  Button,
  Divider,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
} from '@material-ui/core';
import { useHistory} from 'react-router-dom';
import ActionService from '../../../../services/actionService';

import mockData from './data';


const useStyles = makeStyles(theme => ({
  root: {
    padding: theme.spacing(3)
  },
  content: {
    padding: 0
  },
  inner: {
    minWidth: 800
  },
  statusContainer: {
    display: 'flex',
    alignItems: 'center'
  },
  status: {
    marginRight: theme.spacing(1)
  },
  actions: {
    justifyContent: 'flex-end'
  }
}));


const Actions = props => {
  const { className,} = props;

  const classes = useStyles();

  const [status,setStatus] = useState({
    isError:false,
    isSuccess:false,
    isLoading:false
  })

  const [actions,setActions] = useState(mockData);

  const history = useHistory();


  const fetchActions = () =>{
    let actionPromise = ActionService.getAllActions();
    actionPromise.then(actionResponse => {
      //console.log(actionResponse);
      if(actionResponse.status !== 200){
        console.log('Action response not OK : '+actionResponse.status);
        setStatus({
          ...status,
          isError:true
        })
      }else{
        console.log('Action response OK : '+actionResponse.data);
        setActions(actionResponse.data);
        setStatus({
          ...status,
          isSuccess:true
        })
      }
    }).catch()
  } 

  useEffect(() => {
    fetchActions();
  }, []);


  return (
    <div className={classes.root}>
      <Card 
        className={clsx(classes.root, className)}
      >
        <CardHeader
          action={
            <Button
              color="primary"
              onClick={() => {history.push({pathname:'/create-action',isEdit:false})}}
              size="small"
              variant="outlined"
            >
              New Action
            </Button>
          }
          title="Actions"
        />
        <Divider />
        <CardContent className={classes.content}>
          <PerfectScrollbar>
            <div className={classes.inner}>
              <Table>
                <TableHead>
                  <TableRow>
                    <TableCell>Action ID</TableCell>
                    <TableCell >
                        Name                   
                    </TableCell>
                    <TableCell>Main</TableCell>
                    <TableCell>Memory</TableCell>
                    <TableCell>Timeout</TableCell>
                    <TableCell> </TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {actions.map(order => (
                    <TableRow
                      hover
                      key={order.id}
                    >
                      <TableCell>{order.id}</TableCell>
                      <TableCell>{order.name}</TableCell>
                      <TableCell>
                        {order.main}
                      </TableCell>
                      <TableCell>
                        {order.memory}
                      </TableCell>
                      <TableCell>
                        {order.timeout}
                      </TableCell>
                      <TableCell>
                        <div className={classes.statusContainer}>
                          <Button 
                            color="secondary" 
                            onClick={() => {history.push({pathname:'/view-action',isEdit:true,action:order })}}
                            size="small"
                            variant="outlined"
                          >View
                          </Button>
                          {order.status}
                        </div>
                      </TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </div>
          </PerfectScrollbar>
        </CardContent>
        <Divider />
      </Card>
    </div>
  );
};

Actions.propTypes = {
  className: PropTypes.string
};

export default Actions;
