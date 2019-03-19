import React from 'react';
import Card from '@material-ui/core/Card';
import CardHeader from '@material-ui/core/CardHeader';
import Avatar from '@material-ui/core/Avatar';
import Tooltip from '@material-ui/core/Tooltip';
import { format } from 'date-fns';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faBeer, faCocktail, faCoffee, faGlassWhiskey } from '@fortawesome/free-solid-svg-icons';
import './FeedItem.css';

const ACHIEVEMENT = 'ACHIEVEMENT';

const typeToIconMap = {
  COCKTAIL: faCocktail,
  BEER: faBeer,
  SHOT: faGlassWhiskey,
  SOFTDRINK: faCoffee,
};
function AchievementItem(news) {
  const newsMeta = news.news;
  const payload = news.payload.AchievementPayload;
  const user = news.user;

  return (<Card className="feedItem">
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
function DrinkItem(news) {
  const newsMeta = news.news;
  const drinkPayload = news.payload.DrinkPayload;
  const user = news.user;
  return (<Card className="feedItem">
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

export default function FeedItem(props) {
  return props.news.news.newsType === ACHIEVEMENT ?
    AchievementItem(props.news) : DrinkItem(props.news);
}
