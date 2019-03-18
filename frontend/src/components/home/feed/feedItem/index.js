import React, { Component } from 'react';
import Card from '@material-ui/core/Card';
import CardHeader from '@material-ui/core/CardHeader';
import CardMedia from '@material-ui/core/CardMedia';
import CardContent from '@material-ui/core/CardContent';
import Avatar from '@material-ui/core/Avatar';
import Typography from '@material-ui/core/Typography';
import './FeedItem.css';


export default class FeedItem extends Component {
  render() {
    return (
      <Card className="feedItem">
        <CardHeader
          avatar={
            <Avatar aria-label="Recipe" >
              OPA
            </Avatar>
          }
          title="Shrimp and Chorizo Paella"
          subheader="September 14, 2016"
        />
        <CardMedia
          image="/static/images/cards/paella.jpg"
          title="Paella dish"
        />
        {/*<CardContent>*/}
          {/*<Typography component="p">*/}
            {/*This impressive paella is a perfect party dish and a fun meal to cook together with your*/}
            {/*guests. Add 1 cup of frozen peas along with the mussels, if you like.*/}
          {/*</Typography>*/}
        {/*</CardContent>*/}

      </Card>
    );
  }
}
