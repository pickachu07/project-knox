/* eslint-disable react/no-multi-comp */
import React from 'react';
import Downshift from 'downshift';
import matchSorter from 'match-sorter';
import styled from 'styled-components';
import TextField from '@material-ui/core/TextField';
import { 
  Card,
  CardHeader,
  CardContent,
  Divider
} from '@material-ui/core';

const Highlight = styled.span`
    color: red;
    background: yellow;
`;

const LogRow = ({ match, children }) => {
  const _match = match.toLowerCase();

  const chunks = match.length
    ? children.split(new RegExp('(' + match + ')', 'ig'))
    : [children];

  return (
    <div>
      {chunks.map(
        chunk =>
          chunk.toLowerCase() === _match ? (
            <Highlight>{chunk}</Highlight>
          ) : (
            chunk
          )
      )}
    </div>
  );
};

class LogViewer extends React.Component {


  static getDerivedStateFromProps(props, state) {
    return {
      ...state,
      logs: props.logs
    };
  }

  render() {
    const { logs } = this.state;

    return (
      <Card style={{'height' : '100%'}}>
        <CardHeader
          subheader="View logs"
          title="Logs"
        />
        <Divider />
        <CardContent>
          <Downshift>
            {({ getInputProps, inputValue }) => {
              const filtered =
                        (!inputValue && logs) || matchSorter(logs, inputValue);

              return (
            
                <div>
                  
                  <TextField
                
                    {...getInputProps()}
                    placeholder="Filter logs ..."
                  />
                 
                  <p>{filtered.length} matches</p>
                  <Divider />
                  <pre>
                    {filtered.map(log => (
                      <LogRow match={inputValue}>{log}</LogRow>
                    ))}
                  </pre>
                </div>
              
              );
            }}
          </Downshift>
        </CardContent>
      </Card>
      
    );
  }
}

export default LogViewer;