<h1>Android Nano Degree Baking Time App</h1>

<b>Project Overview</b>
<p>In this project, we were tasked to productionize an app, taking it from a funtional state to a production-ready state.</p>

<p>This involved:
  <ul>
    <li>Finding and handling error cases</li>
    <li>Adding accessibility features</li>
    <li>Allowing for localization</li>
    <li>Adding a Widget</li>
    <li>Adding Third-party libraries</li>
  </ul>
</p>

<b>Why this Project?</b>
<p> As a working Android developer, we often create and implement apps where we are responsible for designing and planning the steps needed to create a production-ready app.</p>
<p>Unlike Popular Movies (the previous assignment in the Nano Degree), it was up to us to figure things out for the Baking Time app.</p>

<b>What Did I Learn?</b>
<p>In this project I:
<ul>
  <li>Used MediaPlayer/ExoPlayer to display videos</li>
  <li>Handled error cases using AlertDialogs:
    <ul>
      <li>for calling the Network resource</li>
      <li>for the ExoPlayer</li>
    </ul>
  </li>
  <li>Adding a Widget to the Baking Time app</li>
  <li>Leveraged Third-party libraries, including:
    <ul>
      <li>ButterKnife</li>
      <li>Dagger 2</li>
      <li>Retrofit 2</li>
      <li>Gson</li>
      <li>RxJava 2</li>
      <li>Glide</li>
      <li>Stetho</li>
    </ul>
  </li>
  <li>Used Fragments to create a responsive design that works on phones and tablets</li>
</ul>
</p>

<b>Below are screenshots from the Baking Time app:</b>
<p><em>Select a Recipe - Portrait (Phone)</em><p/>
<img src="https://github.com/AndreaWolff/AndroidNanoDegreeBakingApp/blob/master/images/Phone-Recipe-Menu.png" height="450" width="250">

<p><em>Select Recipe Detail View - Portrait (Phone)</em></p>
<img src="https://github.com/AndreaWolff/AndroidNanoDegreeBakingApp/blob/master/images/Phone-Recipe-Details.png" height="450" width="250">

<p><em>Select Recipe Step Detail View - Portrait (Phone)</em></p>
<img src="https://github.com/AndreaWolff/AndroidNanoDegreeBakingApp/blob/master/images/Phone-Recipe-Instructions.png" height="450" width="250">
<img src="https://github.com/AndreaWolff/AndroidNanoDegreeBakingApp/blob/master/images/Phone-Recipe-Instruction-No-Video.png" height="450" width="250">

<p><em>Select Recipe Step Detail View - Landscape (Phone)</em></p>
<img src="https://github.com/AndreaWolff/AndroidNanoDegreeBakingApp/blob/master/images/Phone-Recipe-Details-Landscape.png" height="250" width="450">
<img src="https://github.com/AndreaWolff/AndroidNanoDegreeBakingApp/blob/master/images/Phone-Recipe-Instruction-Landscape.png" height="250" width="450">

<p><em>Select a Recipe (Tablet)</em></p>
<img src="https://github.com/AndreaWolff/AndroidNanoDegreeBakingApp/blob/master/images/Tablet-Recipe-Menu-Landscape.png" height="350" width="600">

<p><em>Select and View Recipe Step Detail - (Tablet)</em></p>
<img src="https://github.com/AndreaWolff/AndroidNanoDegreeBakingApp/blob/master/images/Tablet-Recipe-Details-Landscape.png" height="350" width="600">
<img src="https://github.com/AndreaWolff/AndroidNanoDegreeBakingApp/blob/master/images/Tablet-Recipe-Instruction-Landscape.png" height="350" width="600">
<img src="https://github.com/AndreaWolff/AndroidNanoDegreeBakingApp/blob/master/images/Tablet-Recipe-Instruction-No-Video-Landscape.png" height="350" width="600">

<img src="https://github.com/AndreaWolff/AndroidNanoDegreeBakingApp/blob/master/images/Tablet-Recipe-Menu.png" height="600" width="350">
<img src="https://github.com/AndreaWolff/AndroidNanoDegreeBakingApp/blob/master/images/Tablet-Recipe-Instruction.png" height="600" width="350">

<p><em>Ingredients Widget</em></p>
<img src="https://github.com/AndreaWolff/AndroidNanoDegreeBakingApp/blob/master/images/Phone-Recipe-Ingredients-Widget.png" height="450" width="250">

<p><em>Ingredients Widget Not Configured</em></p>
<img src="https://github.com/AndreaWolff/AndroidNanoDegreeBakingApp/blob/master/images/Phone-Widget-Not-Configured.png" height="450" width="250">

<p>What the Baking Time App does:
<ul>
  <li>Displays recipes retrieved from a Network resource</li>
  <li>Allows navigation between individual recipes and recipe steps</li>
  <li>Uses Recyclerviews and handles recipe steps with/without images or videos</li>
  <li>Uses the Master Detail flow to display recipe steps and navigate between them</li>
  <li>Uses Exoplayer to display videos</li>
  <li>Uses Glide to display images</li>
  <li>Uses Unit testing and Espresso Automation testing to navigate through the Baking Time app Customer Journey</li>
  <li>Uses many Third-party libraries</li>
  <li>Includes a homescreen Widget of the ingredients of a selected recipe</li>
</ul>
</p>

<h1>Enjoy!</h1>
