import { combineReducers } from 'redux'

const tickets = (state = [], action) => {
  switch (action.type) {
    case 'ADD_TICKET':
      return state
    case 'GET_TICKET':
      return action.newState
    default:
      return state
  }
}

const bioDelaApp = combineReducers({
  tickets
})

export default bioDelaApp;

/*

{
  user: {},
  tickets: [
    {}
  ]
}

*/