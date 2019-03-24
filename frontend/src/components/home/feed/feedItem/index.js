import React from 'react';
import Card from '@material-ui/core/Card';
import CardHeader from '@material-ui/core/CardHeader';
import Avatar from '@material-ui/core/Avatar';
import Tooltip from '@material-ui/core/Tooltip';
import { format } from 'date-fns';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { withStyles } from '@material-ui/core/styles';
import { faBeer, faCocktail, faCoffee, faGlassWhiskey } from '@fortawesome/free-solid-svg-icons';
import './FeedItem.css';

const ACHIEVEMENT = 'ACHIEVEMENT';

const typeToIconMap = {
  COCKTAIL: faCocktail,
  BEER: faBeer,
  SHOT: faGlassWhiskey,
  SOFTDRINK: faCoffee,
};
const styles = theme => ({
  root: {
    background: `
     repeating-linear-gradient(
    0deg,
    transparent,
    transparent 2px,
    #666 2px,
    transparent 4px
  );`,
    border: '1px solid #333',
    // boxShadow: '0 0 4px 2px #0ff',

  },
});

function AchievementItem(news, classes, rest) {
  const newsMeta = news.news;
  const payload = news.payload.AchievementPayload;
  const user = news.user;

  return (<Card {...rest} className={['feedItem', classes.root, rest.className].join(' ')} >
    <CardHeader
      avatar={
        <Tooltip title={`${payload.name}:${payload.description}`}>
          <Avatar alt={payload.name} src={payload.imagePath} />
        </Tooltip>
      }
      title={`${user.name} hat "${payload.name}" erreicht`}
      subheader={format(new Date(newsMeta.createdAt), 'DD.MM.YYYY HH:mm')}
    />

  </Card>);
}
function DrinkItem(news, classes, rest) {
  const newsMeta = news.news;
  const drinkPayload = news.payload.DrinkPayload;
  const user = news.user;
  return (<Card {...rest} className={['feedItem', classes.root, rest.className].join(' ')} >
    <CardHeader
      avatar={
        <Avatar aria-label="Recipe" >
          <FontAwesomeIcon icon={typeToIconMap[drinkPayload.type]} size="1x" />
        </Avatar>
      }
      title={`${user.name} hat ${newsMeta.amount}x ${drinkPayload.name} bestellt`}
      subheader={format(new Date(newsMeta.createdAt), 'DD.MM.YYYY HH:mm')}
    />

  </Card>);
}

function FeedItem(props) {
  const {
    news, key, classes, ...rest
  } = props;
  return props.news.news.newsType === ACHIEVEMENT ?
    AchievementItem(news, classes, rest) : DrinkItem(news, classes, rest);
}
export default withStyles(styles)(FeedItem);
