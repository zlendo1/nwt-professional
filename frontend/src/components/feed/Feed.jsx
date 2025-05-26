import "./feed.css"
import Jobs from "../jobs/Jobs"
import Share from "../share/Share"
import Post from "../post/Post"

export default function Feed() {
  return (
    <div className="feed">
        <div className="feedWrapper">
            <Jobs/>
            <Share/>
            <Post/>
        </div>
    </div>
  )
}
