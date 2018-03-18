import React, { Component } from 'react';
import './style.css';

class GetTicket extends Component {
  constructor(props) {
    super(props);
    this.state = { numberOfTickets: 0 }
  }

  componentDidMount() {
    this.getNumberOfTickets();
  }

  getNumberOfTickets() {
    fetch('http://192.168.0.9:8080/api/tickets/available?sessiontoken=' + localStorage.sessiontoken, {
      method: 'GET',
      mode: 'cors',
      headers: new Headers({
        'Content-Type': 'application/json'
      })
    })
    .then(response => {
      return response.text();
    })
    .then(data => {
      this.setState({ numberOfTickets: parseInt(data) });
    })
  }

  getTicket() {
    fetch('http://192.168.0.9:8080/api/tickets/claim?sessiontoken=' + localStorage.sessiontoken, {
      method: 'GET',
      mode: 'cors',
      headers: new Headers({
        'Content-Type': 'application/json'
      })
    })
    .then(response => {
      return response.json();
    })
    .then(data => {
      this.props.getMyTickets()
      this.getNumberOfTickets();
    })
  }

  render() {
    return (
      <div className="horizontalCentering">
        <div>
          <div className="horizontalCentering" style={{ fontSize: "7em" }}>
            {this.state.numberOfTickets}
          </div>
          <button
            disabled={this.state.numberOfTickets <= 0}
            onClick={() => this.getTicket()}
          >
            Tillkalla Etern
          </button>
        </div>
      </div>
    )
  }
}

export default GetTicket;