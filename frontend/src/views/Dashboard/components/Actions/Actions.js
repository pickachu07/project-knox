import React, { useState } from 'react';
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
  const { className, ...rest } = props;

  const classes = useStyles();

  const [actions] = useState(mockData);

  const history = useHistory();

  return (
    <div className={classes.root}>
      <Card 
        className={clsx(classes.root, className)}
      >
        <CardHeader
          action={
            <Button
              color="primary"
              size="small"
              variant="outlined"
              onClick={() => {history.push({pathname:'/create-action',isEdit:false})}}
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
                    <TableCell>Action Id</TableCell>
                    <TableCell>FIU ID</TableCell>
                    <TableCell >
                        name                   
                    </TableCell>
                    <TableCell>main</TableCell>
                    <TableCell>memory</TableCell>
                    <TableCell>timeout</TableCell>
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
                      <TableCell>{order.fiu_id}</TableCell>
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
                            size="small"
                            variant="outlined"
                            onClick={() => {history.push({pathname:'/view-action',isEdit:true,action:order })}}
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
