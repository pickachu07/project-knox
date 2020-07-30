import uuid from 'uuid/v1';

export default [
  {
    'executionid': uuid(),
    'archived': 'false',
    'status': 'DATA_PENDING',
    'lastupdated': '2020-07-28 11:47:24',
    'fiuid': 'FIU-1',
    'actionid': 'ACTION-1',
    'fisessioniid': uuid(),
    'activationid': uuid(),
    'output': {
      'result': {
        'msg': 'Hello world'
      },
      'size': 21,
      'status': 'success',
      'success': true
    },
    'metadata': {
      'start': 0,
      'end': 0,
      'duration': 0,
      'logs': [
        'string'
      ],
      'cause': 'string',
      'statusCode': 0
    },
    'ttl': '86400'
  },
  {
    'executionid': 'UUID',
    'archived': 'false',
    'status': 'DATA_PENDING',
    'lastupdated': '2020-07-28 11:47:24',
    'fiuid': 'FIU-1',
    'actionid': 'ACTION-1',
    'fisessioniid': 'UUID',
    'activationid': 'd249820a5c54432689820a5c54332611',
    'output': {
      'result': {
        'msg': 'Hello world'
      },
      'size': 21,
      'status': 'success',
      'success': true
    },
    'metadata': {
      'start': 0,
      'end': 0,
      'duration': 0,
      'logs': [
        'string'
      ],
      'cause': 'string',
      'statusCode': 0
    },
    'ttl': '86400'
  },
]