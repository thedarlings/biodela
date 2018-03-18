import React, { Component } from 'react';
import './style.css';

class AddTicket extends Component {
  constructor(props) {
    super(props);
    this.state = { code: "", date: new Date() };
  }

  addTicket() {
    fetch('http://192.168.0.9:8080/api/tickets?sessiontoken=' + localStorage.sessiontoken, {
      body: JSON.stringify({ code: this.state.code, expiryDate: this.state.date.toString() }),
      method: 'POST',
      mode: 'cors',
      headers: new Headers({
        'Content-Type': 'application/json'
      })
    })
    .then(response => {
      console.log(response)
    })
  }

  render() {
    return (
      <div className="centering">
        <div onMouseEnter={() => console.log(this.state)}>
          <p>Skicka din biljett till Etern!</p>
          <input
            type="text"
            placeholder="xxxxxxxxxx"
            value={this.state.code}
            onChange={(event) => this.setState({ code: event.target.value })}
          />
          <input
            type="date"
            onChange={(event) => this.setState({ date: event.target.value })}
          />
          <button onClick={() => this.addTicket()}>Till Etern, och vidare!</button>
        </div>
      </div>
    )
  }
}

export default AddTicket;