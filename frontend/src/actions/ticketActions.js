//Create
export const ADD_TICKET = 'ADD_TICKET'


//Read
export const GET_TICKET = 'GET_TICKET'


export const addTicket = (id, code, date) => {
    return {
        type: 'ADD_TICKET',
        code,
        date
    }
}

export const receivedTicket = (newState) => {
    return {
        type: 'GET_TICKET',
        newState
    }
}


 
//These are the action types Also ordered in CRUD Order.

//Create

//The dispatch and getstate function is provided by the Redux-Thunk middleware, we can dispatch actions with it.
