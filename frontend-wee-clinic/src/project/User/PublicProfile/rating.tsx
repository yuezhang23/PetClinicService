import React, { useState } from 'react';
import { FaStar } from 'react-icons/fa';

const RatingDialog: React.FC<{ contents: string[] }> = ({ contents }) => {
  // Initialize the state for each content's rating
  const [ratings, setRatings] = useState<number[]>(Array(contents.length).fill(0));

  // Handler to set the rating for a specific content
  const handleRating = (index: number, ratingValue: number) => {
    const newRatings = [...ratings];
    newRatings[index] = ratingValue; // Update the rating for the specific content
    setRatings(newRatings); // Update the state with new ratings
  };

  return (
    <div className="rating-dialog">
      {contents.map((content: string, index1: number) => (
        <div key={index1} className="row d-flex flex-grow-1">
          <div className='col-8'>
            {content}
          </div>
          <div className="col ms-3 star-rating">
            {[...Array(5)].map((_, index) => {
              const ratingValue = index + 1;
              return (
                <label key={ratingValue}>
                  <input
                    type="checkbox"
                    name="rating"
                    value={ratingValue}
                    checked={ratings[index1] >= ratingValue}
                    onChange={() => handleRating(index1, ratingValue)} // Pass index of content and rating value
                    style={{ display: 'none' }}
                  />
                  <FaStar
                    className="star"
                    color={ratingValue <= ratings[index1] ? "#ffc107" : "#e4e5e9"}
                    size={30}
                    onClick={() => handleRating(index1, ratingValue)} // Update the correct rating
                    style={{ cursor: 'pointer' }}
                  />
                </label>
              );
            })}
          </div>
        </div>
      ))}
    </div>
  );
};

export default RatingDialog;
