import React, { Component } from 'react';
import './style.css';

class TicketList extends Component {
  render() {
    return (
      <div className="horizontalCentering">
        <div>
          <table>
            <tbody>
              {this.props.tickets.map((ticket, index) => {
                return <tr key={index}>
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