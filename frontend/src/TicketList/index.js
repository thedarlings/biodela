import React, { Component } from 'react';
import './style.css';

class TicketList extends Component {
  constructor(props) {
    super(props);
    this.state = { tickets: [] }
  }

  componentWillMount() {
    this.setState({ tickets: this.props.tickets });
  }

  render() {
    return (
      <div className="horizontalCentering">
        <div>
          <table>
            <tbody>
              {this.props.tickets.map(ticket => {
                return <tr key={ticket.code}>
                  <td>{ticket.code}</td>
                  <td>{ticket.expiryDate}</td>
                </tr>
              })}
            </tbody>
          </table>
        </div>
      </div>
    )
  }
}

export default TicketList;