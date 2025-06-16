import "./post.css";
import { MoreVert } from "@mui/icons-material";
import React from 'react'; // Dobra praksa je uvijek importati React

// Komponenta sada prima 'post' objekt kao prop
export default function Post({ post }) {
    // Ako 'post' nije proslijeđen ili je prazan, možemo prikazati poruku ili ništa
    if (!post) {
        return null; // Ili neki placeholder
    }

    // Dohvaćanje podataka o autoru iz 'post.user' objekta
    // Uz fallback vrijednosti ako neki podaci nedostaju
    const authorName = (post.user && post.user.firstName && post.user.lastName)
        ? `${post.user.firstName} ${post.user.lastName}`
        : (post.user && post.user.username) // Ako imaš username umjesto firstName/lastName
            ? post.user.username
            : 'Nepoznat autor';
    const authorProfileImg = post.user?.profilePicture || "assets/person/default-avatar.png"; // Stavi defaultnu putanju do avatara

    // Pretpostavimo da post može imati svoju sliku (npr. post.imageUrl)
    // Ako nema, nećemo prikazivati <img> tag za sliku posta
    const postImageSrc = post.imageUrl || ""; // Ako tvoj PostDto ima imageUrl polje

    return (
        <div className="post">
            <div className="postWrapper">
                <div className="postTop">
                    <div className="postTopLeft">
                        <img className="postProfileImg" src={authorProfileImg} alt={authorName} />
                        <span className="postName">{authorName}</span>
                        {/* Možemo dodati i datum posta ako ga imamo */}
                        {post.postDate && <span className="postDate" style={{marginLeft: '10px', fontSize: '12px', color: 'gray'}}>{new Date(post.postDate).toLocaleDateString()}</span>}
                    </div>
                    <div className="postTopRight">
                        <MoreVert />
                    </div>
                </div>
                <div className="postCenter">
                    {/* Prikazujemo tekst posta iz propsa */}
                    <span className="postText">{post.text || "Nema sadržaja."}</span>
                    {/* Ako post ima sliku, prikaži je */}
                    {postImageSrc && <img className="postImage" src={postImageSrc} alt="Post slika" />}
                </div>
                {/* Ovdje bi kasnije došli lajkovi, komentari itd. */}
                {/* <div className="postBottom">
          <div className="postBottomLeft">
            <img className="likeIcon" src="assets/like.png" alt="" />
            <img className="likeIcon" src="assets/heart.png" alt="" />
            <span className="postLikeCounter">X people like it</span>
          </div>
          <div className="postBottomRight">
            <span className="postCommentText">Y comments</span>
          </div>
        </div> */}
            </div>
        </div>
    );
}