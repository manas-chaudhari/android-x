# android-x
An experimental project to develop good practices/patterns

# Objectives
1. TDD
1. MVVM
1. Minimise boilerplate (and explore tools/libs)
  1. Dagger
  1. Butterknife
  1. RxJava
  1. Custom Annotation Processor (if required)
1. CI integration

# Demo App Requirements
1. Async operations
  1. Database/Network operations
1. Navigation
1. List/RecyclerView (for Child ViewModels)


# Demo App Design
Github Api for fetching data
3 Models: Event, User, Repository

## Events Page
List of public events.
Links from each event:
- User Profile (Actor)
- Repository

## User Profile Page:
Shows list of repos

## Repository Page:
Shows list of contributors

