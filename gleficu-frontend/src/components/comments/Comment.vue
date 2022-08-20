<template>
  <div class="comment">
    <div class="comment__flex">
      <p style="font-size: larger; font-style: italic">{{ this.commentUsername }}</p>
      <br/>
      <Rating v-if="this.star > 0"
          :star="this.star"
          :disabled="true"
          :maxstars="this.maxStars"
          :starsize="this.starSize"
          :hasresults="this.hasResults"
          :hasdescription="this.hasDescription"
          :ratingdescription="this.ratingDescription"
          @starsNumberUpdate="updateStarsNumber"
      />
      <p>{{ this.commentText }}</p>
      <div class="comment__flex-btn">
        <button @click.prevent="addComplaint()" class="update rounded bg-red-700 text-black cursor-auto" v-if="canAddComplaint()">Report</button>
        <button @click.prevent="deleteComplaint()" class="update rounded bg-red-700 text-black cursor-auto" v-if="canRemoveComplaint()">Remove report</button>
        <button @click.prevent="updateCommentVar = true" @input="updateCommentVar=false" class="update rounded bg-yellow-500 text-black cursor-auto" v-if="canEditComment()">Update</button>
        <button @click.prevent="deleteComment()" class="del rounded bg-yellow-500 text-black cursor-auto" v-if="canEditComment() || adminPrivileges()">Delete</button>
      </div>
    </div>

    <update-comment-modal @commentUpdated2="updatedComment()" :comment-db="this.commentDb" :value="this.updateCommentVar"/>

<!--    <ReportCommentModal v-model="reportComment"/>-->
  </div>
</template>

<script>
import Rating from "@/components/comments/Rating";
// import ReportCommentModal from "@/components/comments/ReportCommentModal";
import {authService} from "@/services/authService";
import {commentService} from "@/services/commentService";
import UpdateCommentModal from "@/components/comments/UpdateCommentModal";
import {userService} from "@/services/userService";

export default {
  name: "Comment",
  components: {
    UpdateCommentModal,
    // ReportCommentModal,
    Rating
  },
  props: {
    commentDb: {
      required: true,
    }
  },
  data() {
    return {
      star: 0,
      ratingDescription: [
        {
          text: "Poor",
          class: "star-poor",
        },
        {
          text: "Below Average",
          class: "star-belowAverage",
        },
        {
          text: "Average",
          class: "star-average",
        },
        {
          text: "Good",
          class: "star-good",
        },
        {
          text: "Excellent",
          class: "star-excellent",
        },
      ],
      hasResults: false,
      hasDescription: true,
      starSize: "lg", //[xs,lg,1x,2x,3x,4x,5x,6x,7x,8x,9x,10x],
      maxStars: 5,
      disabled: false,
      updateCommentVar: false,
      userComplaintsDb: [],
      commentUsername: "",
      commentText: "",
    }
  },
  methods: {
    openReportComment() {
      this.reportComment = true;
    },
    isAdmin() {
      return authService.isAdmin()
    },
    isUser() {
      return authService.isUser()
    },
    updatedComment() {
      this.updateCommentVar=false;
      this.$emit('commentUpdated3');
    },
    deleteComment() {
      commentService.deleteComment(parseInt(this.commentDb.id)).then(() => {
        alert("Comment deleted!");
        this.$emit("deletedComment")
      });
      // emit event to refresh comments
    },
    addComplaint() {
      let userId = parseInt(this.getUserId());
      let commentId = this.commentDb.id;
      let complaint = {
        comment_id: parseInt(commentId),
        user_id: userId
      };
      commentService.addComplaint(complaint).then((response) => {
        alert("Complaint added!");
        console.log("ADD COMPLAINT:");
        console.log(response.data);
      });
    },
    getComplaint() {
      let userId = parseInt(this.getUserId());
      let commentId = parseInt(this.commentDb.id);
      commentService.getComplaint(userId, commentId).then((response) => {
        console.log("GET COMPLAINTS:");
        console.log(response.data);
        this.userComplaintsDb = response.data;
      });
    },
    deleteComplaint() {
      let complaintId = parseInt(this.userComplaintsDb[0].id);
      let commentId = parseInt(this.commentDb.id);
      commentService.deleteComplaint(complaintId, commentId).then((response) => {
        alert("Complaint deleted!");
        console.log("DELETE COMPLAINT:");
        console.log(response.data);
      });
    },
    canEditComment() {
      let userId = parseInt(this.getUserId());
      return this.commentDb.user_id === userId && authService.isUser();
    },
    adminPrivileges() {
      return authService.getJwtField("authority") === "ADMIN";
    },
    canRemoveComplaint() {
      let userId = parseInt(this.getUserId());
      return this.commentDb.user_id !== userId && authService.isUser() && this.userComplaintsDb.length > 0;
    },
    canAddComplaint() {
      let userId = parseInt(this.getUserId());
      return this.commentDb.user_id !== userId && authService.isUser() && this.userComplaintsDb.length === 0;
    },
    getUserId() {
      return authService.getJwtField("userId");
    },
    getUserDb(id) {
      userService.getById(id).then((response) => {
        this.commentUsername = response.data.username;
      })
    },
    updateStarsNumber(stars) {
      this.star = stars;
    }
  },
  mounted() {
    if(this.commentDb) {
      this.star = this.commentDb.like_stars;
      this.getUserDb(this.commentDb.user_id);
      this.commentText = this.commentDb.text;
    }
    this.getComplaint();
  }
}
</script>

<style>
.message {
  margin-bottom: 2em;
}
.comment {
  margin: 1em 0 2em;
  border-bottom: 1px solid #ccc;
}
.comment__flex-btn {
  margin: 1em 0;
}
button + button {
  margin-left: 1em;
}
button {
  padding: 0.75em 1em;
  border: unset;
}
.del {
  background: #f93943;
  color: #000807;
  height: 40px;
  width: 100px;
}
.update {
  background: #7eb2dd;
  color: #000807;
  height: 40px;
  width: 100px;
}
textarea {
  width: 100%;
}
.open {
  margin: 1em 0;
}
</style>