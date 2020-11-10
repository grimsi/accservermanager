import { Component, OnInit } from '@angular/core';
import { NavMenuItem } from '../../models/objects/NavMenuItem';
import { Title } from '@angular/platform-browser';
import { Config } from '../../config/Config';
import { Icon } from '../../models/enums/Icon';
import { DropDownMenuItem } from '../../models/objects/DropDownMenuItem';
import { UserService } from '../../services/user.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  tabNavItems: NavMenuItem[] = [
    new NavMenuItem('Servers', Icon.dns, '/servers', true),
    new NavMenuItem('Events', Icon.list_alt, '/events', true),
    new NavMenuItem('Results', Icon.directions_car, '/results', false),
    new NavMenuItem('Logs', Icon.description, '/logs', false),
    new NavMenuItem('Info', Icon.info, '/info', true)
  ];

  dropDownItems: DropDownMenuItem[] = [
    new DropDownMenuItem('Log out', Icon.lock_open, () => {
      this.authService.logout();
    }, true)
  ];

  activeItem: NavMenuItem;
  userService: UserService;

  constructor(private titleService: Title,
              userService: UserService,
              private authService: AuthService) {
    this.userService = userService;
  }

  ngOnInit() {
  }

  private setActiveItem(item: NavMenuItem) {
    this.activeItem = item;
    this.titleService.setTitle(`${Config.baseTitle} - ${item.title}`);
  }
}
